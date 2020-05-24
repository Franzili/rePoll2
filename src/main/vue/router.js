import VueRouter from "vue-router";
import CreatePoll from "./pages/CreatePoll";
import Start from "./pages/Start";
import Account from "./pages/Account";
import Poll from "./pages/Poll";
import Answer from "./pages/Answer";
import PollTable from "./pages/PollTable";
import PollSetup from "./pages/PollSetup";
import AnswerPoll from "./pages/AnswerPoll";

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
            //meta: {
            //    requiresAuth: true
            //}
        },
        {
            path: '/config/',
            name: 'config',
            component: PollSetup,
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
        }
    ]
});


// Route Guard. Run before each routing.
router.beforeEach((to, from , next) => {
    // if we are not authenticated, redirect to login page.

    // if auth is needed
    if (to.matched.some(route => route.meta.requiresAuth)) {
        // if auth is correct proceed to destination
        if (store.getters.isAuthenticated) {
            next()
        } else {
            next("/");
        }
    // if auth is not needed but acquired go to polls if '/' is requested
    } else if (store.getters.isAuthenticated && to.path === "/") {
        next("/polls/");
    // base case if nothing is needed and acquired
    } else {
        next()
    }
    /*if (!store.state.authenticated && to.path !== "/") {
        next("/");
    }*/

    /*else {
        // else proceed to the route as planned.
        next();
    }*/
});

export default router;
