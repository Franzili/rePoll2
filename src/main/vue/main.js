import Vue from 'vue'
import VueRouter from "vue-router";
import { BootstrapVue, BootstrapVueIcons } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import router from './router';
import store from './store/store';
import App from './App.vue'
import Vuex from 'vuex'


Vue.config.productionTip = false;

Vue.use(VueRouter);
Vue.use(BootstrapVue);
Vue.use(BootstrapVueIcons);
Vue.use(Vuex)

async function main() {
    // load auth from storage so we don't need to login if there is a token already set.
    await store.dispatch('auth/loadFromStorage');

    new Vue({
        render: h => h(App),
        router,
        store: store,
    }).$mount('#app');
}

main();
