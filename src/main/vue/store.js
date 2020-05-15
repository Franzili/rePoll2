import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex)

export default new Vuex.Store({
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
    getters: {
        getSurvey: (state) => {
            return (id) => {
                return state.surveys.find(survey => survey.id == id)
            }
        }/*,
        isAuthenticated: (state) => {
            return state.authenticated
        }*/
    }
})
