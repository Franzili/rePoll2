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
            return new Promise((resolve, reject) => {
                commit('loadStatus', "LOADING");
                api.poll.list().then(function (res) {
                    commit('load', res.data);
                    commit('loadStatus', "DONE")
                    resolve();
                }).catch(function (error) {
                    console.log(error);
                    reject();
                });
            });
        },
        create({commit}, pollCmd) {
            return new Promise((resolve, reject) => {
                api.poll.create(pollCmd).then(function (res) {
                    commit('currentPoll/set', res.data, {root: true});
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

export default myPolls;
