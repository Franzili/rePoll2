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
            api.poll.get(id).then(function (res) {
                commit('setCurrentPoll', res.data);
            }).catch(function (error) {
                console.log(error);
            });
        }
    },

    namespaced: true
}

export default currentPoll;
