import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";
import api from "./api";

Vue.use(Vuex)

const store = new Vuex.Store({
    state: {
        token: null,
        authenticated: null,
        polls: [],
        statistics: []
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
        requestPolls({commit}) {
            return new Promise((resolve, reject) => {
                api.poll.list().then(res => {
                    commit('setPolls', res.data)
                    resolve()
                }).catch(() => {
                    commit('setPolls', [])
                    reject()
                })
            })
        },
        requestPoll({commit}, id) {
            return new Promise((resolve, reject) => {
                api.poll.get(id).then(res => {
                    commit('updatePoll', res.data)
                    resolve()
                }).catch(() => {
                    reject()
                })
            })
        },
        savePoll({commit}, poll) {
            return new Promise((resolve, reject) => {
                api.poll.save(poll).then(res => {
                    commit('updatePoll', res.data)
                    resolve()
                }).catch(() => {
                    reject()
                })
            })
        },
        updatePoll({commit}, poll) {
            return new Promise((resolve, reject) => {
                api.poll.update(poll).then(res => {
                    commit('updatePoll', res.data)
                    resolve()
                }).catch(() => {
                    reject()
                })
            })
        },
        getStatisticQuestion({commit}, pollID, questionID) {
            return new Promise((resolve, reject) => {
                api.statistics.getQuestion(pollID, questionID).then(res => {
                    commit('updateStatisticsMeta', res.data)
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
        setPolls(state, polls) {
            this.state.polls = polls
        },
        savePolls(state, poll) {
            this.state.polls.push(poll)
        },
        updatePoll(state, poll) {
            let index = this.state.polls.findIndex(a => a.id === poll.id)
            this.state.polls[index] = poll
        },
        updateStatisticsMeta(state, statistics) {
            let index = this.state.polls.findIndex(a => a.id === statistics.id)
            this.state.statistics[index] = statistics
        }

    },
    getters: {
        getPoll: (state) => {
            return (id) => {
                return state.polls.find(poll => poll.id === id)
            }
        },
        isAuthenticated: (state) => {
            return state.authenticated
        },
        getStatistics: (state) => {
            return (id) => {
                return state.statistics.find(poll => poll.id === id)
            }
        }
    }
})

store.subscribe((mutation, state) => {
    localStorage.setItem('store', JSON.stringify(state));
    axios.defaults.headers.common['Authorization'] = state.token;
    axios.get('/api/v1/polls/').then((result) => {
        console.log(result);
    }).catch((error) => {console.log(error.message)});
});

export default store;
