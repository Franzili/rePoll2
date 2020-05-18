import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";

import api from "./api";

Vue.use(Vuex)

const store = new Vuex.Store({
    state: {
        token: null,
        authenticated: null
    },

    actions: {
        requestToken({commit}, credentials) {
            return new Promise((resolve, reject) => {
                api.auth.login(credentials.username, credentials.password)
                    .then(function(res) {
                        console.log("Authentication successful.");
                        let token = res.headers.authorization
                        commit('authenticate', token)
                        resolve()
                    })
                    .catch(function() {
                        console.log("Authentication failed. Clearing tokens.")
                        commit('authenticate', null)
                        reject()
                    });
            })
        },
    },

    mutations: {
        authenticate(state, token) {
            if (token !== null) {
                this.state.token = token
                this.state.authenticated = true
                axios.defaults.headers['Authorization'] = token
            } else {
                this.state.authenticated = false
            }
        },

        initializeStore(state) {
            // Get content from localStorage and set state with it.
            if (localStorage.getItem('store')) {
                this.replaceState(
                    Object.assign(state, JSON.parse(localStorage.getItem('store')) )
                )
            }
        }
    },
})

store.subscribe((mutation, state) => {
    localStorage.setItem('store', JSON.stringify(state));
    axios.defaults.headers['Authorization'] = state.token
});

export default store;
