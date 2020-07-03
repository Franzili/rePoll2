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
            Object.assign(obj, iterationCmd);

            // if this was the current iteration and it is now closed
            if (this.state.currentPoll.poll.currentIteration.id === iterationCmd.id &&
                iterationCmd.status === "CLOSED") {
                this.state.currentPoll.poll.currentIteration = null;

                // update end date
                this.state.currentPoll.poll.pollIterations.find(item => item.id === iterationCmd.id).end = new Date();
            }
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
        },
        scheduleNew({dispatch}) {
            // set start to one day from now
            let start = new Date();
            start.setDate(start.getDate() + 1);

            let iterationCmd = {
                status: "SCHEDULED",
                start: start,
                end: null
            }
            return dispatch("create", iterationCmd);
        },
        closeNow({dispatch}, id) {
            let iterationCmd = {
                id,
                status: "CLOSED"
            }
            return dispatch("update", iterationCmd);
        }
    },

    namespaced: true
}

export default iterations;
