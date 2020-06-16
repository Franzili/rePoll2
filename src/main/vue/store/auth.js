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
                        commit('authenticate', false)
                        reject()
                    });
            })
        },

        logout({commit}) {
            return new Promise((resolve) => {
                commit('logOut')
                localStorage.removeItem('authToken')
                localStorage.removeItem('username')
                resolve()
            })

        },


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

            if (token === null) {
                state.authenticated = null; //not yet tried to login
                console.log('null')
            } else if (!token) {
                state.authenticated = false; //failed login
                console.log('false')
            } else {
                state.authenticated = true; //successfull login
            }

            localStorage.setItem('authToken', token);


        },
        /**
         * sets the username
         */
        setUsername(state, username) {
            state.username = username;


            localStorage.setItem('username', username)
        },
        logOut(state) {
            state.username = '';
            state.token = '';
        }

    },

    namespaced: true
}

export default auth;
