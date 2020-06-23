import api from "../api";

const myIterations = {
    state: {
        /**
         * The iterations the user has access to
         */
        iterations: [],
        /**
         * The loading status. Will be "LOADING" if the iterations are being reloaded right now, otherwise it will be
         * "DONE". Can be used for loading spinners etc.
         */
        loadStatus: "LOADING"
    },
    getters: {

    },
    mutations: {
        /**
         *  Sets the iterations that are currently loaded. For internal use.
         */
        load(state, iterations) {
            state.iterations = iterations
        },
        /**
         * Sets the load status. For internal use.
         */
        loadStatus(state, status) {
            state.loadStatus = status
        },
        create(state, iteration) {
            state.iterations.push(iteration)
        },
        delete(state, id) {
            state.iterations.filter(iteration => iteration.id !== id)
        },
        update(state, iteration) {
            let IndexOldIteration = state.iterations.findIndex(oldIteration => oldIteration.id === iteration.id)
            state.iterations[IndexOldIteration] = iteration;
        }
    },
    actions: {
        /**
         * Re-loads the iterations from backend.
         * Will set loadStatus to "DONE" once finished.
         */
        load({rootState, commit}) {
            if (rootState.currentPoll.poll.id === undefined || rootState.currentPoll.poll.id === null) {
                console.warn("PollId is undefined");
                return;
            }
            /*return new Promise((resolve, reject) => {
                api.iterations.listAll(id).then(function (res) {
                    commit('set', res.data);
                    resolve(res.data);
                }).catch(function (error) {
                    console.log(error);
                    reject();
                });
            }*/
            return new Promise((resolve, reject) => {
                commit('loadStatus', "LOADING");
                api.iterations.listAll(rootState.currentPoll.poll.id).then(function (res) {
                    commit('load', res.data);
                    commit('loadStatus', "DONE")
                    resolve();
                }).catch(function (error) {
                    console.log(error);
                    reject();
                });
            });
        },
        /**
         * Creates a new iteration. Accesses currentIteration store module to set the newly created
         * iteration as the current one.
         */
        create({rootState, commit}, pollIterationCmd) {
            if (rootState.currentPoll.poll.id === undefined || rootState.currentPoll.poll.id === null) {
                console.warn("PollId is undefined");
                return;
            }
            /*return new Promise(function (resolve, reject) {
                api.iterations.addIter(rootState.currentPoll.poll.id, pollIterationCmd).then(() => {
                    resolve();
                }).catch(function (error) {
                    console.log(error);
                    reject();
                })
            }*/
            return new Promise((resolve, reject) => {
                api.iterations.addIter(rootState.currentPoll.poll.id, pollIterationCmd).then(function (res) {
                    //commit('currentIteration/set', res.data, {root: true});
                    commit('create', res.data);
                    resolve(res.data);
                }).catch(function (error) {
                    console.log(error);
                    reject();
                });
            });
        },
        /**
         * Deletes a Iteration
         */
        delete({commit, rootState, dispatch}, id) {
            if (rootState.currentPoll.poll.id === undefined || rootState.currentPoll.poll.id === null) {
                console.warn("PollId is undefined");
                return;
            }
            /*return new Promise((resolve, reject) => {
                api.iterations.removeIter(rootState.currentPoll.poll.id, id).then(function () {
                    commit('delete', id);
                    resolve();
                }).catch(function (error) {
                    console.log(error);
                    reject(error);
                })
            }*/
            return new Promise((resolve, reject) => {
                api.iteration.removeIter(rootState.currentPoll.poll.id, id).then(function (res) {
                    //commit('currentIteration/set', {}, {root: true})
                    commit('delete', id);
                    dispatch('load')
                    resolve(res.data);
                }).catch(function (error) {
                    console.log(error);
                    reject();
                })
            })
        },
        update({commit, rootState, dispatch}, pollIterationCmd) {
            if (rootState.currentPoll.poll.id === undefined || rootState.currentPoll.poll.id === null) {
                console.warn("PollId is undefined");
                return;
            }
            return new Promise((resolve, reject) => {
                api.iterations.update(rootState.currentPoll.poll.id, pollIterationCmd).then(function (res) {
                    commit('update', res.data);
                    dispatch('load')
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

export default myIterations;
