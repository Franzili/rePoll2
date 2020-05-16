import VueRouter from "vue-router";
import CreateSurvey from "./pages/CreateSurvey";
import Start from "./pages/Start";
import Account from "./pages/Account";
import SurveyTable from "./pages/SurveyTable";
import SurveySetup from "./pages/SurveySetup";
import AnswerSurvey from "./pages/AnswerSurvey";

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
            component: SurveyTable
        },
        {
            path: '/config/',
            component: SurveySetup
        },
        {
            path: '/answer/',
            component: AnswerSurvey
        }
    ]
})
