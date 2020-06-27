import Vue from 'vue';

import api from "../api";

import SectionHeaderModel from "./poll-item-models/SectionHeaderModel";


const currentIteration = {

    state: {
        iteration: {
           start: "",
           end: "",
           status: ""
        }
    },
    getters: {

    },
    mutations: {
        /**
         * Sets the new current iteration.
         */
        set(state, newIteration) {
            state.iteration = newIteration
        },
        /**
         * Updates some iteration parameters.
         * The pollIterationCmd object may not be a full poll object. All parameters will be copied, so
         * a partial object can be supplied.
         */
        update(state, pollIterationCmd) {
            Object.assign(state.iteration, pollIterationCmd)
        },
    },
    actions: {
        /**
         * Reloads the Iteration from Backend
         */
        load({rootState, commit}, id) {
            if (rootState.currentPoll.poll.id === undefined || rootState.currentPoll.poll.id === null) {
                console.warn("PollId is undefined");
                return;
            }
            return new Promise((resolve, reject) => {
                api.iterations.getIter(rootState.currentPoll.poll.id,id).then(function (res) {
                    commit('set', res.data);
                    resolve(res.data);
                }).catch(function (error) {
                    console.log(error);
                    reject();
                });
            });
        },
        /**
         * Updates a Iteration
         */
        update({commit, rootState}, pollIterationCmd) {
            if (rootState.currentPoll.poll.id === undefined || rootState.currentPoll.poll.id === null) {
                console.warn("PollId is undefined");
                return;
            }
            return new Promise(function(resolve, reject)  {
                api.iterations.updateIter(rootState.currentPoll.poll.id, pollIterationCmd).then(() => {
                    commit('update', pollIterationCmd);
                    resolve();
                }).catch(function (error) {
                    console.log(error);
                    reject();
                })
            })
        },
        /**
         * Creates a new Iteration
         */
        create({rootState}, pollIterationCmd) {
            if (rootState.currentPoll.poll.id === undefined || rootState.currentPoll.poll.id === null) {
                console.warn("PollId is undefined");
                return;
            }
            return new Promise(function (resolve, reject) {
                api.iterations.addIter(rootState.currentPoll.poll.id, pollIterationCmd).then(() => {
                    resolve();
                }).catch(function (error) {
                    console.log(error);
                    reject();
                })
            })
        },
        /**
         * Deletes a Iteration
         */
        delete({commit, rootState}, id) {
            if (rootState.currentPoll.poll.id === undefined || rootState.currentPoll.poll.id === null) {
                console.warn("PollId is undefined");
                return;
            }
            return new Promise((resolve, reject) => {
                api.iterations.removeIter(rootState.currentPoll.poll.id, id).then(function () {
                    commit('delete', id);
                    resolve();
                }).catch(function (error) {
                    console.log(error);
                    reject(error);
                })
            })
        }
    },

    namespaced: true
}

export default currentIteration;
