import Vue from 'vue'
import VueRouter from "vue-router";
import { BootstrapVue, BootstrapVueIcons } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

import router from './router';
import store from './store';

import App from './App.vue'

Vue.config.productionTip = false;

Vue.use(VueRouter);
Vue.use(BootstrapVue);
Vue.use(BootstrapVueIcons);

new Vue({
    render: h => h(App),
    router,
    store,
    //beforeCreate: () => this.$store.commit('initializeStore')
}).$mount('#app');
