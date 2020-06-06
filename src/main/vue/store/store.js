import Vuex from "vuex";
import currentPoll from "./currentPoll";
import myPolls from "./myPolls";
import auth from "./auth";
import Vue from "vue";

Vue.use(Vuex);

/**
 * Store consists of these modules:
 * - currentPoll:  Holds the poll currently open
 * - myPolls:  Holds all polls the user has access to
 * - auth:  Holds the users's authentication (i.e. their JWT token)
 */
let store = new Vuex.Store({
    modules: {
        currentPoll: currentPoll,
        myPolls: myPolls,
        auth: auth
    }
})

// load auth from storage so we don't need to login if there is a token already set.
store.dispatch('auth/loadFromStorage');

export default store;
