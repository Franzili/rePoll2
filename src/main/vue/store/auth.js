import api from "../api";
import axios from "axios";

const auth = {
    state: {
        authenticated: null,
        token: null
    },

    getters: {
        authenticated: state => state.authenticated
    },

    actions: {
        requestToken({commit}, credentials) {
            return new Promise((resolve, reject) => {
                api.auth.login(credentials.username, credentials.password)
                    .then(function (res) {
                        let token = res.headers.authorization
                        console.log("Davor!");
                        commit('authenticate', token)
                        console.log("Danach!");
                        resolve()
                    })
                    .catch(function () {
                        console.log("Authentication failed. Clearing tokens.")
                        commit('authenticate', null)
                        reject()
                    });
            })
        },
    },

    mutations: {
        authenticate(state, token) {
            state.token = token;
            axios.defaults.headers['Authorization'] = token

            if (!token) {
                state.authenticated = false;
            } else {
                state.authenticated = true;
            }

            localStorage.setItem('authToken', token);
        },

        logout({commit}) {
            commit('authenticate', null);
        },

        loadFromStorage({commit}) {
            let token = localStorage.getItem('authToken');
            commit('authenticate', token);
        }
    },

    namespaced: true
}

export default auth;
