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
    mutations: { //adding poll id . Fixing bug: each poll gets all iterations
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
         * Creates a new iteration for the poll
         */
        create({rootState, commit}, pollIterationCmd) {
            if (rootState.currentPoll.poll.id === undefined || rootState.currentPoll.poll.id === null) {
                console.warn("PollId is undefined");
                return;
            }
            return new Promise((resolve, reject) => {
                api.iterations.addIter(rootState.currentPoll.poll.id, pollIterationCmd).then(function (res) {
                    commit('create', res.data);
                    resolve(res.data);
                }).catch(function (error) {
                    console.log(error);
                    reject();
                });
            });
        },
        /**
         * Deletes an iteration
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
                api.iterations.removeIter(rootState.currentPoll.poll.id, id).then(function (res) {
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
        /**
         * Updates an iteration with new information in Cmd-object
         */
        update({commit, dispatch, rootState}, id, pollIterationCmd) {
            if (rootState.currentPoll.poll.id === undefined || rootState.currentPoll.poll.id === null) {
                console.warn("PollId is undefined");
                return;
            }
            //console.log('test: ',pollIterationCmd)
            //console.log(pollIterationCmd.end)
            //console.log(pollIterationCmd.status)
            return new Promise((resolve, reject) => {
                api.iterations.updateIter(rootState.currentPoll.poll.id, id , pollIterationCmd).then(function (res) {
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
