import api from "../api";

/**
 * Contains the polls that the current user has access to.
 */
const myPolls = {
    state: {
        /**
         * The polls the user has access to
         */
        polls: [],

        /**
         * The loading status. Will be "LOADING" if the polls are being reloaded right now, otherwise it will be
         * "DONE". Can be used for loading spinners etc.
         */
        loadStatus: "LOADING"
    },

    mutations: {
        /**
         *  Sets the polls that are currently loaded. For internal use.
         */
        load(state, polls) {
            state.polls = polls
        },
        /**
         * Sets the load status. For internal use.
         */
        loadStatus(state, status) {
            state.loadStatus = status
        }
    },

    actions: {
        /**
         * Re-loads the polls from backend.
         * Will set loadStatus to "DONE" once finished.
         */
        load({commit}) {
            return new Promise((resolve, reject) => {
                commit('loadStatus', "LOADING");
                api.poll.list().then(function (res) {
                    commit('load', res.data);
                    commit('loadStatus', "DONE")
                    resolve();
                }).catch(function (error) {
                    console.log(error);
                    reject();
                });
            });
        },
        /**
         * Creates a new poll. Accesses currentPoll store module to set the newly created poll as the
         * current one.
         */
        create({commit}, pollCmd) {
            return new Promise((resolve, reject) => {
                api.poll.create(pollCmd).then(function (res) {
                    commit('currentPoll/set', res.data, {root: true});
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

export default myPolls;
