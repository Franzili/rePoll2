import Vue from "vue";
import Vuex from "vuex";
import api from "./api";

Vue.use(Vuex)

export default new Vuex.Store({
    /*
    state: {
        authenticated: null, //
        surveys: [
            {
                id: 1,
                name: "is there a survey ?",
                status: "READY",
                items: [
                    {
                        id: 1,
                        type: "checkbox",
                        question: "am I working",
                        possibilities: [
                            {
                                id: 1,
                                text: "yes",
                            },
                            {
                                id: 2,
                                text: "no"
                            }
                        ]
                    },
                    {
                        id: 2,
                        type: "checkbox",
                        question: "need more questions",
                        possibilities: [
                            {
                                id: 1,
                                text: "yes",
                            },
                            {
                                id: 2,
                                text: "no"
                            }
                        ]
                    }
                ]
            },
            {
                id: 2,
                name: "are there two ?",
                status: "READY",
                items: [
                    {
                        id: 1,
                        type: "checkbox",
                        question: "am I working",
                        possibilities: [
                            {
                                id: 1,
                                text: "yes",
                            },
                            {
                                id: 2,
                                text: "no"
                            }
                        ]
                    },
                    {
                        id: 2,
                        type: "checkbox",
                        question: "need more questions",
                        possibilities: [
                            {
                                id: 1,
                                text: "yes",
                            },
                            {
                                id: 2,
                                text: "no"
                            }
                        ]
                    }
                ]
            }
        ],
    },
    */
    state: {
        //authenticated: null,
        surveys: [],
    },
    mutations: {
        setSurveys(state, surveys) {
            this.state.surveys = surveys
        },
        updateSurvey(state, survey) {
            let index = this.state.surveys.findIndex(a => a.id === survey.id)
            this.state.surveys[index] = survey
        }
        /*
        ,
        for saving a new survey
        saveSurvey(state, survey) {
            let id = this.state.surveys.length
            survey.id = id + 1
            this.state.surveys.push(survey)
        }
        */
    },
    actions: {
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
    getters: {
        getSurvey: (state) => {
            return (id) => {
                return state.surveys.find(survey => survey.id === id)
            }
        }/*,
        isAuthenticated: (state) => {
            return state.authenticated
        }*/
    }
})
