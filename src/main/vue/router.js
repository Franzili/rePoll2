import VueRouter from "vue-router";
import Start from "./pages/Start";
import Account from "./pages/Account";
import Answer from "./pages/Answer";
import PollTable from "./pages/PollTable";
import Admin from "./pages/Admin";
import PollResponse from "./pages/PollResponse";

import PollTabbed from "./pages/PollTabbed";
import EditPoll from "./components/poll-tabs/edit/EditPoll";

import store from "./store/store";
import TestCharts from "./pages/TestCharts";
import PollStats from "./components/poll-tabs/stats/PollStats";
import ConfigurePoll from "./components/poll-tabs/configure/ConfigurePoll";

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
                requiresPrivileges: "ROLE_POLL_EDITOR"
            }
        },
        {
            path: '/polls/',
            name: 'polls',
            component: PollTable,
            meta: {
                requiresPrivileges: "ROLE_POLL_EDITOR"
            }
        },
        {
            path: '/poll/:id/answer',
            component: Answer,
            name: 'answer',
        },
        {
            path: '/poll/:pollId/',
            component: PollTabbed,
            meta: {
                requiresPrivileges: "ROLE_POLL_EDITOR"
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
                requiresPrivileges: "ROLE_ADMIN"
            }
        },
        {
            path: '/poll-response/',
            component: PollResponse,
            name: 'response',
        },
        //for Charts testing
        {
            path: '/test/',
            component: TestCharts
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
        // TODO: redirect to 403 page
        next("/polls/");
    }
});

router.beforeEach((to, from, next) => {
    console.debug("[RePoll] Routing to " + to.path);
    next();
})

export default router;

