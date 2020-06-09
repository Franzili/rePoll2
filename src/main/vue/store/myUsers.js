import api from "../api";

const myUsers = {
    state: {
        users: [],

    },
    mutations: {
        load(state, users) {
            state.users = users
        },
        create(state, user) {
            state.users.push(user)
        },
        delete(state, id) {
            state.users.filter(user => user.id !== id)
        },
        update(state, user) {
            let IndexOldUser = state.users.findIndex(oldUser => oldUser.id === user.id)
            state.users[IndexOldUser] = user;
        }
    },
    actions: {
        load({commit}) {
            return new Promise((resolve, reject) => {
                api.users.list().then(function (res) {
                    commit('load',res.data);
                    resolve()
                }).catch(function (error) {
                    console.log(error);
                    reject();
                })
            })
        },
        create({commit}, userCmd) {
            return new Promise((resolve, reject) => {
                api.users.create(userCmd).then(function (res) {
                    commit('create', res.data);
                    resolve(res.data)
                }).catch(function (error) {
                    console.log(error);
                    reject();
                })
            })
        },
        delete({commit, dispatch}, id) {
            return new Promise((resolve, reject) => {
                api.users.delete(id).then(function (res) {
                    commit('delete', id);
                    dispatch('load')
                    resolve(res.data)
                }).catch(function (error) {
                    console.log(error);
                    reject();
                })
            })
        },
        update({commit}, userCmd) {
            return new Promise((resolve, reject) => {
                api.users.update(userCmd).then(function (res) {
                    commit('update', res.data);
                    resolve(res.data)
                }).catch(function (error) {
                    console.log(error);
                    reject();
                })
            })
        }

    },
    namespaced: true
}

export default myUsers;
