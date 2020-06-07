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
            let res = [];
            state.poll.pollSections.forEach(section => {
                res.push(new SectionHeader(section.id, section.title, section.description));
                section.questions.forEach(q => {
                    let questionObject = state.poll.questions.find(item => item.id === q.id);
                    res.push(makeQuestion(questionObject));
                });
            });
            return res;
        },

        statStructureObj: state => {
            console.log(' hi ich bin im gtetter anfang')
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
            console.log(' bye bye ich bin am Ende des getters')
            return strObj
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


        setMetaStats(state, newMetaStats) {
            state.statistics = newMetaStats
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
        }
    },

    namespaced: true
}

export default currentPoll;
