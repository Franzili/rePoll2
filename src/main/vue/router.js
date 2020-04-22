import VueRouter from "vue-router";
import CreateSurvey from "./components/CreateSurvey";
import Start from "./components/Start";
import Account from "./components/Account";

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
        }
    ]
})
