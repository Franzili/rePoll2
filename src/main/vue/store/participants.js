import api from "../api";

const participants = {
    state: {
        participants: []
    },
    getters: {},
    mutations: {
        set(state, newParticipants) {
            state.participants = newParticipants;
        }
    },
    actions: {
        load({commit}, id) {
            return new Promise((resolve, reject) => {
                api.poll.listParticipants(id).then(function (res) {
                    commit('set', res.data);
                    resolve(res.data);
                }).catch(function (error) {
                    console.log(error);
                    reject();
                });
            });
        },
        update({commit,state}, participantCmd) {
            return new Promise(function(resolve, reject)  {
                api.poll.updateParticipant(state.poll.id, participantCmd).then(() => {
                    resolve();
                }).catch(function (error) {
                    console.log(error);
                    reject();
                })
            })
        },


    },
    namespaced: true
}
