import api from "../api";
import axios from "axios";

/**
 * Holds the user authentication.
 */
const auth = {
    state: {
        /**
         * Whether we are authenticated at the moment. (i.e. whether we have a valid JWT token)
         * - true if we are authenticated
         * - false if we are not
         * - null if we have not tried yet
         */
        authenticated: null,
        token: null,
        username: null
    },

    getters: {
        authenticated: state => state.authenticated
    },

    actions: {
        /**
         * Try to authenticate using a username and a password.
         * This will set the token and the 'authenticated' state.
         */
        requestToken({commit}, credentials) {
            commit('setUsername',credentials.username)
            return new Promise((resolve, reject) => {
                api.auth.login(credentials.username, credentials.password)
                    .then(function (res) {
                        let token = res.headers.authorization
                        commit('authenticate', token)
                        resolve()
                    })
                    .catch(function () {
                        console.log("Authentication failed. Clearing tokens.")
                        commit('authenticate', null)
                        reject()
                    });
            })
        },

        /* todo
        logout({commit}) {
            commit('authenticate', null);
        },
         */

        /**
         * Load a token from Browser localStorage.
         */
        loadFromStorage({commit}) {
            let token = localStorage.getItem('authToken');
            commit('authenticate', token);

            let username = localStorage.getItem('username');
            commit('setUsername', username)
        }
    },

    mutations: {
        /**
         * Sets a token as our current authorization methhod and sets up axios to use it everytime we
         * call the backend.
         */
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
        /**
         * sets the username
         */
        setUsername(state, username) {
            state.username = username;


            localStorage.setItem('username', username)
        }

    },

    namespaced: true
}

export default auth;
