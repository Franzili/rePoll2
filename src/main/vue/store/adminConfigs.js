import api from "../api";

const adminConfigs = {
    state: {
        configs: []
    },
    mutations: {
        updateConfigs(state, config) {
            state.configs.push(config)
        }
    },
    actions: {
        updateConfigs({commit}, mailCmd) {
            console.log(mailCmd)
            return new Promise((resolve, reject) => {
                api.admin.updateConfigs(mailCmd).then(function (res) {
                    commit('updateConfigs', res.data);
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

export default adminConfigs;
