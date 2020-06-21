import api from "../api";

const iterations = {
    state: {
        iterations: []
    },
    getters: {

    },
    mutations: {
        set(state, newIterations) {
            state.iterations = newIterations;
        },
        update(state, PollIterationCmd) {
            let IndexOldIteration = state.iterations.findIndex(oldIteration =>
                oldIteration.id === iterations.id);
            state.iterations[IndexOldIteration] = PollIterationCmd;
        },
        delete(state, id) {
            state.iterations.filter(iterations => iterations.id !== id);
        }
    },
    actions: {
        /**
         * Reloads the Iterations from Backend
         */
        load({rootState, commit}, id) {
            if (rootState.currentPoll.poll.id === undefined || rootState.currentPoll.poll.id === null) {
                console.warn("PollId is undefined");
                return;
            }
            return new Promise((resolve, reject) => {
                api.iterations.listAll(id).then(function (res) {
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

export default iterations;
