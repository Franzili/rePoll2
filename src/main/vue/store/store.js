import Vuex from "vuex";
import currentPoll from "./currentPoll";
import myPolls from "./myPolls";
import auth from "./auth";
import Vue from "vue";

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        polls: [],
    },

    mutations: {
        setPolls(state, polls) {
            this.state.polls = polls
        },
        savePolls(state, poll) {
            this.state.polls.push(poll)
        },
    },

    modules: {
        currentPoll: currentPoll,
        myPolls: myPolls,
        auth: auth
    }
})

