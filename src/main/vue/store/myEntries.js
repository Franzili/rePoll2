import api from "../api";

export default {
    state: {
        entry: {}

    },
    mutations: {
        setEntry(state, newEntry) {
            state.entry = newEntry
        }
    },
    actions: {
        rootEntry({commit}, entrysOfPoll) {
            return new Promise((resolve, reject) => {
                api.entry.answerFirst(entrysOfPoll).then(function (res) {
                    commit('setEntry', res.data);
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

/*
auto: {reifen: 4, fenster: 8}
bau(auto){
braucheReifen = auto.reifen //=4
}
 */
