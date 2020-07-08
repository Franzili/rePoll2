import VueRouter from "vue-router";

import Start from "./pages/Start";
import Account from "./pages/Account";
import Answer from "./pages/Answer";
import PollTable from "./pages/PollTable";
import Admin from "./pages/Admin";
import PollResponse from "./pages/PollResponse";

import TestCharts from "./pages/TestCharts";

import PollTabbed from "./pages/PollTabbed";
import EditPoll from "./components/poll-tabs/edit/EditPoll";
import PollStats from "./components/poll-tabs/stats/PollStats";
import ConfigurePoll from "./components/poll-tabs/configure/ConfigurePoll";

import Forbidden from "./pages/error/Forbidden";
import NotFound from "./pages/error/NotFound";

import store from "./store/store";

let router = new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/',
            component: Start
        },
        {
            path: '/account/',
            component: Account,
            meta: {
                requiresPrivileges: "ROLE_POLL_EDITOR",
                title: "My Account | RePoll"
            }
        },
        {
            path: '/polls/',
            name: 'polls',
            component: PollTable,
            meta: {
                requiresPrivileges: "ROLE_POLL_EDITOR",
                title: "My Polls | RePoll"
            }
        },
        {
            path: '/answer/:pollId/:participantId?',
            component: Answer,
            name: 'answer',
        },
        {
            path: '/poll/:pollId/',
            component: PollTabbed,
            meta: {
                requiresPrivileges: "ROLE_POLL_EDITOR",
            },
            children: [
                {
                    path: '',
                    name: 'poll-tabbed',
                    redirect: 'config',
                    meta: {
                        requiresPrivileges: "ROLE_POLL_EDITOR"
                    }
                },
                {
                    path: 'edit',
                    name: 'edit-poll',
                    component: EditPoll,
                    meta: {
                        requiresPrivileges: "ROLE_POLL_EDITOR"
                    }
                },
                {
                    path: 'config',
                    name: 'configure-poll',
                    component: ConfigurePoll,
                    meta: {
                        requiresPrivileges: "ROLE_POLL_EDITOR"
                    }
                },
                {
                    path: 'stats',
                    name: 'poll-stats',
                    component: PollStats,
                    meta: {
                        requiresPrivileges: "ROLE_POLL_EDITOR"
                    }
                }
            ]
        },
        {
            path: '/admin/',
            component: Admin,
            name: 'admin',
            meta: {
                requiresPrivileges: "ROLE_ADMIN",
                title: "Server Administration | RePoll"
            }
        },
        {
            path: '/poll-response/',
            component: PollResponse,
            name: 'response',
            meta: {
                title: "Thank you for participating | RePoll"
            }
        },
        //for Charts testing
        {
            path: '/test/',
            component: TestCharts
        },
        {
            name: 'not-found',
            path: '/error/not-found',
            component: NotFound,
            meta: {
                title: "Not found | RePoll"
            }
        },
        {
            name: 'forbidden',
            path: '/error/forbidden',
            component: Forbidden,
            meta: {
                title: "Forbidden | RePoll"
            }
        },
        {
            path: '*',
            redirect: { name: 'not-found' }
        }
    ]
});

router.beforeEach((to, from, next) => {
    console.debug("[RePoll] Requested route: " + to.path);
    next();
})

router.beforeEach((to, from, next) => {
    if (to.meta.requiresPrivileges && !store.state.auth.authenticated) {
        next('/');
    } else {
        next();
    }
})

router.beforeEach((to, from, next) => {
    if (to.path === '/' && store.state.auth.authenticated) {
        next('/polls');
    } else {
        next();
    }
})

router.beforeEach((to, from , next) => {
    let required = to.meta.requiresPrivileges;
    if (store.getters['auth/hasPrivileges'](required)) {
        next();
    } else {
        next("/error/forbidden/");
    }
});

router.beforeEach((to, from, next) => {
    console.debug("[RePoll] Routing to " + to.path);
    next();
})

export default router;

