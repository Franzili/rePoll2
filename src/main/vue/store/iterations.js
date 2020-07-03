import api from "./../api/index";

function makeIteration(obj) {
    if (obj == null) {
        return null;
    }

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
        add(state, iteration) {
            this.state.currentPoll.poll.pollIterations.push(iteration);
            this.state.currentPoll.poll.currentIteration = iteration;
        },
        update(state, iterationCmd) {
            let obj = this.state.currentPoll.poll.pollIterations.find(item => item.id === iterationCmd.id);
            console.log("got iteration cmd: " + JSON.stringify(iterationCmd));
            console.log("got current oibject: " + JSON.stringify(obj));
            Object.assign(obj, iterationCmd);
        }
    },

    actions: {
        update({rootState, commit}, iterationCmd) {
            console.log("In action. got obj: " + JSON.stringify(iterationCmd));
            commit('update', iterationCmd);
            return new Promise((resolve, reject) => {
                api.iterations.update(rootState.currentPoll.poll.id, iterationCmd.id, iterationCmd)
                    .then((res) => {
                        resolve(res.data);
                    })
                    .catch((err) => {
                        console.warn(err);
                        reject(err);
                    })
            })
        },
        async create({rootState, commit}, iterationCmd) {
            try {
                let res = await api.iterations.create(rootState.currentPoll.poll.id, iterationCmd);
                commit("add", res.data);
            } catch(err) {
                console.warn(err);
            }
        },
        openNew({dispatch}) {
            let iterationCmd = {
                status: "OPEN",
                start: new Date(),
                end: null
            }
            return dispatch("create", iterationCmd)
        }
    },

    namespaced: true
}

export default iterations;
