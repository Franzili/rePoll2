import api from "../api";

const myPolls = {
    state: {
        polls: [],
        loadStatus: "LOADING"
    },

    mutations: {
        load(state, polls) {
            state.polls = polls
        },
        loadStatus(state, status) {
            state.loadStatus = status
        }
    },

    actions: {
        load({commit}) {
            commit('loadStatus', "LOADING");
            api.poll.list().then(function (res) {
                commit('load', res.data);
                commit('loadStatus', "DONE")
            }).catch(function (error) {
                console.log(error);
            });
        }
    },

    namespaced: true
}

export default myPolls;
