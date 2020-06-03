import api from "../api";

import {makeQuestion, SectionHeader} from "./poll-item-models/index"

/**
 * currentPoll holds the state of the Poll that is currently open, or otherwise in focus.
 * Can be used for PollTabbed pages (Config, Edit, Stats), and other pages that focus on
 * exactly one poll.
 *
 * currentPoll does some model mapping to make sure that the interface stays constant
 * even if the backend interface changes. The Backend's response is saved in currentPoll.state.poll.
 * However, users of currentPoll should use the mapped attributes on currentPoll instead of
 * currentPoll.state.poll.
 */
const currentPoll = {
    state: {
        poll: {},
        statistics: {}
    },

    getters: {
        pollStructureFlat: state => {
            let res = [];
            state.poll.pollSections.forEach(section => {
                res.push(new SectionHeader(section.title, section.description));
                section.questions.forEach(q => {
                    let questionObject = state.poll.questions.find(item => item.id === q.id);
                    res.push(makeQuestion(questionObject));
                });
            });
            return res;
        },
    },

    mutations: {
        /**
         * Sets the new current poll.
         */
        set(state, newPoll) {
            state.poll = newPoll
        },

        update(state, pollCmd) {
            Object.assign(state.poll, pollCmd)
        },
        setMetaStats(state, newMetaStats) {
            state.statistics = newMetaStats
        }
    },

    actions: {
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
