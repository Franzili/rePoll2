import api from "../api";

import SectionHeaderModel from "./poll-item-models/SectionHeaderModel";

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
        poll: {
            questions: [],
            pollSections: []
        },
        answers: [],
        pollAnswers: [],
        /**
         * The statistics belonging to that poll object.
         */
        statistics: []
    },

    getters: {
        /**
         * Gets the poll structure as a flat array.
         * Array members are either questions or section headers, as defined in the PollItemModel classes
         * in ./poll-item-models/
         */
        pollStructureFlat: state => {
            let result = [];
            state.poll.pollSections.forEach(section => {
                result.push(new SectionHeaderModel(section.id, section.title, section.description));
                let tmp = []
                section.questions.forEach(q => {
                    let questionObject = state.poll.questions.find(item => item.id === q.id);
                    //res.push(makeQuestion(questionObject));
                    tmp.push(questionObject);
                    tmp = tmp.sort((q1, q2) => q1.questionOrder - q2.questionOrder);
                });
                result = result.concat(tmp);
            });
            return result;
        },

        statStructureObj: state => {
            let strObj = [];
            state.poll.pollSections.forEach(section => {
                let statObjList = []
                section.questions.forEach(q => {
                    let stats = state.statistics.find(stat => stat.question.id === q.id)
                    statObjList.push(stats)
                })
                let secObj = {title: section.title,id: section.id, statistics: statObjList}
                strObj.push(secObj)
            })
            return strObj
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
        },
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

        updatePollSection(state, pollSectionCmd) {
            let pollSection = state.poll.pollSections.find(section => section.id === pollSectionCmd.id);
            Object.assign(pollSection, pollSectionCmd);
        },

        updateStructureFromFlat(state, structure) {
            /* this is basically a duplicate of the updateStructure action, but because PollStructureCmd.java and
               state.poll.pollStructure require two slightly different formats, we cannot really avoid it. :(  */

            if (structure[0].type !== 'SectionHeader') {
                console.warn("[RePoll] Invalid poll structure format: first element needs to be header. abort.");
                return;
            }

            // assign the right questionOrders.
            for (let i = 0; i < structure.length; i++) {
                if (structure[i].type !== 'SectionHeader') {
                    structure[i].questionOrder = i + 1;
                }
            }

            let pollSections = []
            let currentSection = null;

            structure.forEach((item) => {
                if (item.type === 'SectionHeader') {
                    if (currentSection != null) {
                        pollSections.push(currentSection);
                    }
                    currentSection = item;
                    currentSection.questions = [];
                } else {
                    currentSection.questions.push(item);
                }
            })
            pollSections.push(currentSection);

            console.log(pollSections);

            state.poll.pollSections = pollSections;
        },

        addQuestion(state, question) {
            state.poll.questions.push(question);
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

        updatePollItem({commit, state}, pollItem) {
            if (pollItem.type === 'SectionHeader') {
                let pollSectionCmd = {
                    id: pollItem.id,
                    title: pollItem.title,
                    description: pollItem.description
                }
                commit('updatePollSection', pollSectionCmd);
                return new Promise(function(resolve, reject) {
                    api.poll.updatePollSection(state.poll.id, pollSectionCmd).then(() => {
                        resolve();
                    }).catch(function(error) {
                        console.log(error);
                        reject();
                    })
                })
            }

            else {
                return new Promise(function(resolve, reject) {
                    api.poll.updateQuestion(state.poll.id, pollItem).then(() => {
                        resolve();
                    }).catch(function(error) {
                        console.log(error);
                        reject();
                    })
                })
            }
        },

        async updateStructure({state, dispatch, commit}, newStructure) {
            if (newStructure[0].type !== 'SectionHeader') {
                console.warn("[RePoll] New poll structure does not start with a header. Aborting.");
                return;
            }

            /* first, assign question orders */
            for (let i = 0; i < newStructure.length; i++) {
                if (newStructure[i].type !== 'SectionHeader') {
                    newStructure[i].questionOrder = i + 1;
                }
            }

            /* then, check if there are any new poll items that have not been POSTed yet to the server */
            let questionPromises = [];
            let updateIndices = [];
            for (let i = 0; i < newStructure.length; i++) {
                if (newStructure[i].id === -1) {
                    questionPromises.push(api.poll.addQuestion(state.poll.id, newStructure[i]))
                    updateIndices.push(i);
                }
            }
            /* now update the newly POSTed poll items to have a proper ID */
            let serverResponse = await Promise.all(questionPromises)
            for (let i = 0; i < questionPromises.length; i++) {
                let updateIndex = updateIndices[i];
                let updatedItem = serverResponse[i].data;
                newStructure[updateIndex] = updatedItem;
                commit('addQuestion', updatedItem);
            }

            let structureCmd = {};
            let currentSectionId = "";

            newStructure.forEach(function (elem) {
                if (elem.type === 'SectionHeader') {
                    currentSectionId = elem.id;
                    structureCmd[currentSectionId] = [];
                } else {
                    structureCmd[currentSectionId].push(elem.id);
                }
            });

            commit('updateStructureFromFlat', newStructure)

            // send all questions to the backend to update question order. This is suuuper bad.-
            let promises = newStructure
                .filter(item => item.type !== 'SectionHeader')
                .map(item => dispatch('updatePollItem', item))

            await Promise.all(promises);
            await api.poll.updateStructure(state.poll.id, structureCmd);
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
        },
    },

    namespaced: true
}

export default currentPoll;
