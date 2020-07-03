import api from "./../api/index";

function makeIteration(obj) {
    let start = null;
    if (obj.start) {
        start = new Date(obj.start);
    }

    let end = null;
    if (obj.end) {
        end = new Date(obj.end);
    }

    return {
        id: obj.id,
        status: obj.status,
        start: start,
        end: end
    };
}

const iterations = {
    state: {},
    getters: {
        iterations(state, getters, rootState) {
            return rootState.currentPoll.poll.pollIterations.map(iteration => makeIteration(iteration));
        },
        current(state, getters, rootState) {
            return makeIteration(rootState.currentPoll.poll.currentIteration);
        },
        scheduled(state, getters) {
            return getters['iterations'].filter(it => it.status === 'SCHEDULED');
        },
        previous(state, getters) {
            return getters['iterations'].filter(it => it.status === 'CLOSED');
        }
    },
    mutations: {
        update(state) {

        }
    },
    actions: {
        update({rootState, commit, state}, iterationCmd) {
            commit('update', iterationCmd);
            return new Promise((resolve, reject) => {
                api.iterations.update(rootState.currentPoll.pollId, iterationCmd)
                    .then((res) => {
                        resolve(res.data);
                    })
                    .catch((err) => {
                        console.warn(err);
                        reject(err);
                    })
            })
        },
        create({rootState}, iterationCmd) {
            return new Promise((resolve, reject) => {
                api.iterations.create(rootState.currentPoll.pollId, iterationCmd.id, iterationCmd)
                    .then((res) => {
                        resolve(res.data)
                    })
                    .catch((err) => {
                        console.warn(err);
                        reject(err)
                    });
            });
        }
    },
    namespaced: true
}

export default iterations;
