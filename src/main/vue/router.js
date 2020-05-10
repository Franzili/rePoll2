import VueRouter from "vue-router";
import CreateSurvey from "./pages/CreateSurvey";
import Start from "./pages/Start";
import Account from "./pages/Account";
import SurveyList from "./pages/SurveyList";
import Survey from "./pages/Survey";
import Answer from "./pages/Answer";

export default new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/',
            component: Start
        },
        {
            path: '/create/',
            component: CreateSurvey
        },
        {
            path: '/account/',
            component: Account
        },
        {
            path: '/surveys/',
            component: SurveyList
        },
        {
            path: '/survey/:id(\\d+)',
            component: Survey
        },
        {
            path: '/survey/:id/answer',
            component: Answer,
            //meta: {
            //    requiresAuth: true
            //}
        }
    ]
})
