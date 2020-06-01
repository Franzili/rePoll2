import api from "../api";

const currentPoll = {
    state: {
        poll: {},
        answers: []
    },

    mutations: {
        set(state, newPoll) {
            state.poll = newPoll
        },
        update(state, pollCmd) {
            Object.assign(state.poll, pollCmd)
        },
        setAnswers(state, newAnswers) {
            state.answers = newAnswers
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
        loadAnswers({commit}, answerCmd) {
            return new Promise((resolve, reject) => {
                api.statistics.getAnswers(answerCmd.poll, answerCmd.quest).then(function (res) {
                    commit('setAnswers', res.data);
                    console.log(res.data)
                    resolve(res.data);
                }).catch(function (error) {
                    console.log(error);
                    reject();
                })
            })
        }
    },

    namespaced: true
}

export default currentPoll;
