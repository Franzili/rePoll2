import VueRouter from "vue-router";
import CreateSurvey from "./pages/CreateSurvey";
import Start from "./pages/Start";
import Account from "./pages/Account";
import SurveyTable from "./pages/SurveyTable";

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
        }
    ]
})
