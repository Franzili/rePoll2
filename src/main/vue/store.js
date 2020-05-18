import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";
import api from "./api";

Vue.use(Vuex)


const store = new Vuex.Store({
    state: {
        token: null,
        authenticated: null,
        surveys: []
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
        requestSurveys({commit}) {
            return new Promise((resolve, reject) => {
                api.survey.list().then(res => {
                    commit('setSurveys', res.data)
                    resolve()
                }).catch(() => {
                    commit('setSurveys', [])
                    reject()
                })
            })
        },
        requestSurvey({commit}, id) {
            return new Promise((resolve, reject) => {
                api.survey.get(id).then(res => {
                    commit('updateSurvey', res.data)
                    resolve()
                }).catch(() => {
                    reject()
                })
            })
        }
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
        },
        setSurveys(state, surveys) {
            this.state.surveys = surveys
        },
        updateSurvey(state, survey) {
            let index = this.state.surveys.findIndex(a => a.id === survey.id)
            this.state.surveys[index] = survey
        }

    },
})

store.subscribe((mutation, state) => {
    localStorage.setItem('store', JSON.stringify(state));
    axios.defaults.headers['Authorization'] = state.token
});

export default store;
