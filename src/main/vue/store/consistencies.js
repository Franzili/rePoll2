import api from "../api";

const consistencies = {
    state: {
        currentConsist: [],
    },

    mutations: {
        load(state, newConsistencies) {
            state.currentConsist = newConsistencies
        },
        set(state, newConsistency) {
            state.currentConsist.push(newConsistency)
        },
        update(state, updConsistency) {
            console.log('bevor update ',state.currentConsist)
            let index = state.currentConsist.findIndex(consistency => consistency.id = updConsistency.id)
            state.currentConsist[index] = updConsistency
            console.log('danach update ',state.currentConsist)

        },
        delete(state, constId) {
            let index = state.currentConsist.findIndex(consistency => consistency.id = constId)
            state.currentConsist.splice(index, 1)
        }
    },

    actions: {
        load({commit}, pollId) {
            return new Promise((resolve, reject) => {
                api.consistency.list(pollId).then(function (res) {
                    commit('load', res.data);
                    resolve(res.data);
                }).catch(function (error) {
                    console.log(error);
                    reject();
                })
            })
        },
        create({commit}, addCmd) {
            return new Promise((resolve, reject) => {
                api.consistency.add(addCmd).then(function (res) {
                    commit('set', res.data);
                    resolve(res.data);
                }).catch(function (error) {
                    console.log(error);
                    reject();
                })
            })
        },
        update({commit}, updComd) {
            return new Promise((resolve, reject) => {
                api.consistency.update(updComd).then(function (res) {
                    commit('update', res.data);
                    resolve(res.data);
                }).catch(function (error) {
                    console.log(error);
                    reject();
                })
            })
        },
        delete({commit/*, dispatch*/}, dltCmd) {
            return new Promise((resolve, reject) => {
                api.consistency.delete(dltCmd).then(function (res) {
                    commit('delete', dltCmd.constId);
                    //dispatch('load', dltCmd.pollId);
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

export default consistencies
