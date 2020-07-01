import api from "../api";

const participants = {
    state: {
        participants: [],
        mailAnswer: ''
    },
    getters: {},
    mutations: {
        set(state, newParticipants) {
            state.participants = newParticipants;
        },
        update(state, participantCmd) {
            let IndexOldParticipant = state.participants.findIndex(oldParticipant =>
                oldParticipant.id === participants.id);
            state.participants[IndexOldParticipant] = participantCmd;
        },
        delete(state, id) {
            state.participants.filter(participants => participants.id !== id);
        },
        add(state, mailAnswer) {
            state.mailAnswer = mailAnswer;
        }
    },
    actions: {
        /**
         * Reloads the Participants from Backend
         */
        loadParticipant({rootState, commit}, id) {
            if (rootState.currentPoll.poll.id === undefined || rootState.currentPoll.poll.id === null) {
                console.warn("PollId is undefined");
                return;
            }
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
        /**
         * Updates a Participant
         */
        update({commit, rootState}, participantCmd) {
            if (rootState.currentPoll.poll.id === undefined || rootState.currentPoll.poll.id === null) {
                console.warn("PollId is undefined");
                return;
            }
            return new Promise(function(resolve, reject)  {
                api.poll.updateParticipant(rootState.currentPoll.poll.id, participantCmd).then(() => {
                    commit('update', participantCmd);
                    resolve();
                }).catch(function (error) {
                    console.log(error);
                    reject();
                })
            })
        },
        /**
         * Creates a new Participant
         */
        create({commit, rootState}, participantCmd) {
            if (rootState.currentPoll.poll.id === undefined || rootState.currentPoll.poll.id === null) {
                console.warn("PollId is undefined");
                return;
            }
            return new Promise(function (resolve, reject) {
                api.poll.addParticipant(rootState.currentPoll.poll.id, participantCmd).then((res) => {
                    commit('add', res.data);
                    resolve();
                }).catch(function (error) {
                    console.log(error);
                    reject();
                })
            })
        },
        /**
         * Deletes a Participant
         */
        delete({commit, rootState}, id) {
            if (rootState.currentPoll.poll.id === undefined || rootState.currentPoll.poll.id === null) {
                console.warn("PollId is undefined");
                return;
            }
            return new Promise((resolve, reject) => {
                api.poll.removeParticipant(rootState.currentPoll.poll.id, id).then(function () {
                    commit('delete', id);
                    resolve();
                }).catch(function (error) {
                    console.log(error);
                    reject(error);
                })
            })
        },

    },
    namespaced: true
}

export default participants;
