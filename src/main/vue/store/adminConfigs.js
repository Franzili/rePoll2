import api from "../api";

const adminConfigs = {
    state: {
        configs: []
    },
    actions: {
        update({commit}, mailCmd) {
            console.log(mailCmd)
            return new Promise((resolve, reject) => {
                api.configs.list().then(function (res) {
                    commit('updateConfigs', res.data);
                    resolve(res.data)
                }).catch(function (error) {
                    console.log(error);
                    reject();
                })
            })
        }
    }
}
