import VueRouter from "vue-router";
import CreatePoll from "./pages/CreatePoll";
import Start from "./pages/Start";
import Account from "./pages/Account";
import Poll from "./pages/Poll";
import Answer from "./pages/Answer";
import PollTable from "./pages/PollTable";
import AnswerPoll from "./pages/AnswerPoll";
import PollTabbed from "./pages/PollTabbed";

import store from "./store/store";

let router = new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/',
            component: Start
        },
        {
            path: '/create/',
            name: 'create',
            component: CreatePoll,
            meta: {
                requiresAuth: true
            }
        },
        {
            path: '/account/',
            component: Account,
            meta: {
                requiresAuth: true
            }
        },
        {
            path: '/polls/',
            component: PollTable,
            meta: {
                requiresAuth: true
            }
        },
        {
            path: '/poll/:id(\\d+)',
            component: Poll,
            meta: {
                requiresAuth: true
            }
        },
        {
            path: '/poll/:id/answer',
            component: Answer,
            meta: {
                requiresAuth: true
            }
        },
        {
            path: '/answer/',
            component: AnswerPoll,
            meta: {
                requiresAuth: true
            }
        },
        {
            path: '/poll-tabbed/',
            component: PollTabbed,
            name: 'poll-tabbed',
            meta: {
                requiresAuth: true
            }
        }
    ]
});


// Route Guard. Run before each routing.
router.beforeEach((to, from , next) => {
    // if we are not authenticated, redirect to login page.

    // if auth is needed
    if (to.matched.some(route => route.meta.requiresAuth)) {
        // if auth is correct proceed to destination
        if (store.state.auth.authenticated) {
            next()
        } else {
            next("/");
        }
    // if auth is not needed but acquired go to polls if '/' is requested
    } else if (store.state.auth.authenticated && to.path === "/") {
        next("/polls/");
    // base case if nothing is needed and acquired
    } else {
        next()
    }
});

export default router;
