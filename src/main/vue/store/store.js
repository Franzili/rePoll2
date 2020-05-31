import Vuex from "vuex";
import currentPoll from "./currentPoll";
import myPolls from "./myPolls";
import auth from "./auth";
import Vue from "vue";

Vue.use(Vuex);

let store = new Vuex.Store({
    modules: {
        currentPoll: currentPoll,
        myPolls: myPolls,
        auth: auth
    }
})

store.dispatch('auth/loadFromStorage');

export default store;
