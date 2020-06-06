import api from "../api";

import {makeQuestion, SectionHeader} from "./poll-item-models/index"

/**
 * currentPoll holds the state of the Poll that is currently open, or otherwise in focus.
 * Can be used for PollTabbed pages (Config, Edit, Stats), and other pages that focus on
 * exactly one poll.
 */
const currentPoll = {
    state: {
        /**
         * The current poll object.
         */
        poll: {},
        answers: [],
        pollAnswers: [],
        /**
         * The statistics belonging to that poll object.
         */
        statistics: {}
    },

    getters: {
        /**
         * Gets the poll structure as a flat array.
         * Array members are either questions or section headers, as defined in the PollItemModel classes
         * in ./poll-item-models/
         */
        pollStructureFlat: state => {
            let res = [];
            state.poll.pollSections.forEach(section => {
                res.push(new SectionHeader(section.id, section.title, section.description));
                console.log(section.questions)
                section.questions.forEach(q => {
                    let questionObject = state.poll.questions.find(item => item.id === q.id);
                    res.push(makeQuestion(questionObject));
                });
            });
            return res;
        },

        pollStructureObj: state => {
            let strObj = [];
            state.poll.pollSections.forEach(section => {
                let qObjList = []
                section.questions.forEach(q => {
                    let choice = {text: q.title, value: q.id}
                    qObjList.push(choice)
                })
                let secObj = {label: section.title, options: qObjList}
                strObj.push(secObj)
            })
            return strObj
        },

        // returns array of objects usable for table
        getAnswerSetByID: (state) => {
            return (id) => {
                let match = Object.entries((state.pollAnswers.find(answerSet => answerSet.question.id === id))
                    .userAnswerMap)
                let tableObj = []
                if (match[0][1].type === 'TextAnswer') {
                    for (let i = 0; i < match.length; i++) {
                        let entry = {Username: match[i][0], Answers: match[i][1].text}
                        tableObj = [...tableObj, entry]
                    }
                    return tableObj

                } else if (match[0][1].type === 'SingleChoiceAnswer') {
                    for (let i = 0; i < match.length; i++) {
                        let entry = {Username: match[i][0], Answers: match[i][1].choice.text}
                        tableObj = [...tableObj, entry]
                    }
                    return tableObj

                } else if (match[0][1].type === 'ScaleAnswer') {
                    for (let i = 0; i < match.length; i++) {
                        let entry = {Username: match[i][0], Answers: match[i][1].scaleNumber}
                        tableObj = [...tableObj, entry]
                    }
                    return tableObj

                } else {
                    for (let i = 0; i < match.length; i++) {
                        let answers = ''
                        for (let j = 0; j < match[i][1].choices.length; j++) {
                            answers = match[i][1].choices[j].text + ', ' + answers
                        }
                        let entry = {Username: match[i][0], Answers: answers}
                        tableObj = [...tableObj, entry]
                    }
                    return tableObj
                }
            }
        }
    },

    mutations: {
        /**
         * Sets the new current poll.
         */
        set(state, newPoll) {
            state.poll = newPoll
        },

        /**
         * Updates some poll parameters.
         * The pollCmd object may not be a full poll object. All parameters will be copied, so
         * a partial object can be supplied.
         */
        update(state, pollCmd) {
            Object.assign(state.poll, pollCmd)
        },

        setMetaStats(state, newMetaStats) {
            state.statistics = newMetaStats
        },

        setAnswersById(state, newAnswers) {
            state.answers = newAnswers
        },

        setPollAnswers(state, newPollAnswers) {
            state.pollAnswers = newPollAnswers
        }
    },

    actions: {
        /**
         * Loads a poll from backend and sets it as the current one.
         */
        load({commit}, id) {
            return new Promise((resolve, reject) => {
                api.poll.get(id).then(function (res) {
                    commit('set', res.data);
                    resolve(res.data);
                }).catch(function (error) {
                    console.log(error);
                    reject();
                });
            });
        },

        /**
         * Updates some poll parameters. And sends them to the backend.
         * The response sent from the backend will be the new poll object.
         * The pollCmd object does not need to be a full poll,
         * but at least the id and the parameters to be changed should be set.
         */
        update({commit}, pollCmd) {
            return new Promise((resolve, reject) => {
                commit('update', pollCmd)
                api.poll.update(pollCmd)
                    .then((res) => {
                        resolve(res.data);
                    })
                    .catch((error) => {
                        console.log(error);
                        reject(error);
                    })
            });
        },
        loadMetaStats({commit}, id) {
            return new Promise((resolve, reject) => {
                api.statistics.get(id).then(function (res) {
                    commit('setMetaStats', res.data);
                    resolve(res.data);
                }).catch(function (error) {
                    console.log(error);
                    reject();
                })
            })
        },
        loadPollAnswers({commit}, id) {
            return new Promise((resolve, reject) => {
                api.statistics.getPollAnswers(id).then(function (res) {
                    commit('setPollAnswers', res.data);
                    resolve(res.data);
                }).catch(function (error) {
                    console.log(error);
                    reject();
                })
            })
        },
        loadAnswersById({commit}, answerCmd) {
            return new Promise((resolve, reject) => {
                api.statistics.getAnswersById(answerCmd.poll, answerCmd.quest).then(function (res) {
                    commit('setAnswersById', res.data);
                    resolve(res.data);
                }).catch(function (error) {
                    console.log(error);
                    reject();
                })
            })
        }
    },

    namespaced: true
}

export default currentPoll;
