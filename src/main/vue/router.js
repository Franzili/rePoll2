import VueRouter from "vue-router";
import CreatePoll from "./components/poll-tabs/edit/EditPoll";
import Start from "./pages/Start";
import Account from "./pages/Account";
import Answer from "./pages/Answer";
import PollTable from "./pages/PollTable";
import Admin from "./pages/Admin";

import PollTabbed from "./pages/PollTabbed";
import EditPoll from "./components/poll-tabs/edit/EditPoll";
import PollStats from "./components/poll-tabs/stats/PollStats";
import ConfigurePoll from "./components/poll-tabs/configure/ConfigurePoll";

import store from "./store/store";
import TestCharts from "./pages/TestCharts";

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
            path: '/poll/:id/answer',
            component: Answer,
            meta: {
                requiresAuth: true
            }
        },
        // todo: change this so we can reach all three tabs (edit, configure, stats)
        {
            path: '/poll/:pollId/',
            component: PollTabbed,
            name: 'poll-tabbed',
            meta: {
                requiresAuth: true
            },
            children: [
                { path: 'edit', name: 'edit-poll', component: EditPoll },
                { path: 'config', name: 'configure-poll', component: ConfigurePoll },
                { path: 'stats', name: 'poll-stats', component: PollStats }
            ]
        },
        {
            path: '/admin/',
            component: Admin,
            name: 'admin',
            meta: {
                requiresAuth: true
            }
        },
        //for Charts testing
        {
            path: '/test/',
            component: TestCharts
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
