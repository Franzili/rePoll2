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
        username: null,
        role: null
    },

    getters: {
        authenticated: state => state.authenticated,

        hasPrivileges(state) {
            return required => {
                switch(required) {
                    case undefined:
                        return true;
                    case "ROLE_ADMIN":
                        return this.hasAdminPrivileges(state);
                    case "ROLE_POLL_CREATOR":
                        return this.hasCreatorPrivileges(state);
                    case "ROLE_POLL_EDITOR":
                        return this.hasEditorPrivileges(state);
                    default:
                        console.warn(`[RePoll] Unknown role ${required}`);
                        return false;
                }
            }
        },
        hasEditorPrivileges:  state => state.role in ["ROLE_ADMIN", "ROLE_POLL_CREATOR", "ROLE_POLL_EDITOR"],
        hasCreatorPrivileges: state => state.role in ["ROLE_ADMIN", "ROLE_POLL_CREATOR"],
        hasAdminPrivileges:   state => state.role in ["ROLE_ADMIN"]
    },

    actions: {
        /**
         * Try to authenticate using a username and a password.
         * This will set the token, the user's current role and the 'authenticated' state.
         */
        async requestToken({commit}, credentials) {
            commit('setUsername', credentials.username)
            try {
                let result = await api.auth.login(credentials.username, credentials.password);
                let token = result.headers.authorization;
                commit('authenticate', token);
            } catch(err) {
                console.warn("[RePoll] Authentication failed. Clearing tokens.")
                commit('authenticate', false);
                return;
            }

            let roleResponse = await api.auth.getRole(credentials.username);
            commit('setRole', roleResponse.data);
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
         * Also gets the user's current role from the server.
         */
        loadFromStorage({commit, dispatch}) {
            let token = localStorage.getItem('authToken');
            commit('authenticate', token);

            let username = localStorage.getItem('username');
            commit('setUsername', username);

            // load role from server as it might have changed since
            // the last time we checked.
            return dispatch('loadRole');
        },

        loadRole({state, commit}) {
            return new Promise(function(resolve, reject) {
                api.auth.getRole(state.username).then((result) => {
                    commit('setRole', result.data);
                    resolve();
                }).catch((error) => {
                    reject(error);
                })
            })
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
            } else if (!token) {
                state.authenticated = false; //failed login
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

        setRole(state, role) {
            state.role = role;
        },

        logOut(state) {
            state.username = '';
            state.token = '';
        }
    },

    namespaced: true
}

export default auth;
