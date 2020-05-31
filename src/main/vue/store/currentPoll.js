import api from "../api";

const currentPoll = {
    state: {
        poll: {}
    },

    mutations: {
        set(state, newPoll) {
            state.poll = newPoll
        },
        update(state, pollCmd) {
            Object.assign(state.poll, pollCmd)
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
                    .then((res) => res.data)
                    .catch((error) => {
                        console.log(error);
                        reject(error);
                    })
            });
        }
    },

    namespaced: true
}

export default currentPoll;
