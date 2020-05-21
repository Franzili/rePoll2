import VueRouter from "vue-router";
import CreateSurvey from "./pages/CreateSurvey";
import Start from "./pages/Start";
import Account from "./pages/Account";
import Survey from "./pages/Survey";
import Answer from "./pages/Answer";
import SurveyTable from "./pages/SurveyTable";
import SurveySetup from "./pages/SurveySetup";
import AnswerSurvey from "./pages/AnswerSurvey";

import store from "./store";

var router = new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/',
            component: Start
        },
        {
            path: '/create/',
            name: 'create',
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
            path: '/survey/:id(\\d+)',
            component: Survey
        },
        {
            path: '/survey/:id/answer',
            component: Answer,
            //meta: {
            //    requiresAuth: true
            //}
        },
        {
            path: '/config/',
            name: 'config',
            component: SurveySetup
        },
        {
            path: '/answer/',
            component: AnswerSurvey
        }
    ]
});


// Route Guard. Run before each routing.
router.beforeEach((to, from , next) => {
    // if we are not authenticated, redirect to login page.
    if (!store.state.authenticated && to.path !== "/") {
        next("/");
    }

    if (store.state.authenticated && to.path === "/") {
        next("/surveys");
    }

    else {
        // else proceed to the route as planned.
        next();
    }
});

export default router;
