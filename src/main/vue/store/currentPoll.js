import api from "../api";

const currentPoll = {
    state: {
        poll: {},
        statistics: {}
    },

    mutations: {
        set(state, newPoll) {
            state.poll = newPoll
        },
        update(state, pollCmd) {
            Object.assign(state.poll, pollCmd)
        },
        setMetaStats(state, newMetaStats) {
            state.statistics = newMetaStats
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
                    .then((res) => {
                        resolve(res.data);
                    })
                    .catch((error) => {
                        console.log(error);
                        reject(error);
                    })
            });
        },
        loadMetaStats({commit}, id) {
            return new Promise((resolve, reject) => {
                api.statistics.get(id).then(function (res) {
                    commit('setMetaStats', res.data);
                    resolve(res.data);
                }).catch(function (error) {
                    console.log(error);
                    reject();
                })
            })
        },
    },

    namespaced: true
}

export default currentPoll;
