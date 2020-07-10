import api from "../api";

const adminConfigs = {
    state: {
        configs: []
    },
    mutations: {
        updateConfigs(state, config) {
            state.configs = config
        },
        loadConfigs(state, config) {
            state.configs = config
        }
    },
    actions: {
        /**
         * Update the Mail Configurations.
         */
        updateConfigs({commit}, mailCmd) {
            return new Promise((resolve, reject) => {
                api.admin.updateConfigs(mailCmd).then(function (res) {
                    commit('updateConfigs', res.data);
                    resolve(res.data)
                }).catch(function (error) {
                    console.log(error);
                    reject();
                })
            })
        },
        /**
         * Get the current configurations from the backend.
         */
        getConfigs({commit}) {
            return new Promise((resolve, reject) => {
                api.admin.getConfigs().then(function (res) {
                    commit('loadConfigs',res.data);
                    resolve()
                }).catch(function (error) {
                    console.log(error);
                    reject();
                })
            })
        },
    },

    namespaced: true
}

export default adminConfigs;
