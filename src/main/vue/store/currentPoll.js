import api from "../api";

const currentPoll = {
    state: {
        poll: {}
    },

    mutations: {
        set({state}, newPoll) {
            state.poll = newPoll
        }
    },

    actions: {
        load({commit}, id) {
            return new Promise((resolve, reject) => {
                api.poll.get(id).then(function (res) {
                    commit('setCurrentPoll', res.data);
                    resolve(res.data);
                }).catch(function (error) {
                    console.log(error);
                    reject();
                });
            });
        }
    },

    namespaced: true
}

export default currentPoll;
