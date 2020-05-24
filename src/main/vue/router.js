import VueRouter from "vue-router";
import CreatePoll from "./pages/CreatePoll";
import Start from "./pages/Start";
import Account from "./pages/Account";
import Poll from "./pages/Poll";
import Answer from "./pages/Answer";
import PollTable from "./pages/PollTable";
import PollSetup from "./pages/PollSetup";
import AnswerPoll from "./pages/AnswerPoll";
import PollTabbed from "./pages/PollTabbed";

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
            component: CreatePoll
        },
        {
            path: '/account/',
            component: Account
        },
        {
            path: '/polls/',
            component: PollTable
        },
        {
            path: '/poll/:id(\\d+)',
            component: Poll
        },
        {
            path: '/poll/:id/answer',
            component: Answer,
            //meta: {
            //    requiresAuth: true
            //}
        },
        {
            path: '/config/',
            name: 'config',
            component: PollSetup
        },
        {
            path: '/answer/',
            component: AnswerPoll
        },
        {
            path: '/poll-tabbed/',
            component: PollTabbed
            /* meta: {
                requiresAuth: true
            }
             */
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
        next("/polls");
    }

    else {
        // else proceed to the route as planned.
        next();
    }
});

export default router;
