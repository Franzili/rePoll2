<template>
  <div id="app">
      <!-- not good but rare -->
      <NavBar v-show="
      this.$router.currentRoute.path !== '/'
      && $route.name !== 'answer'
      && $route.name !== 'response'"></NavBar>
      <router-view></router-view>
  </div>
</template>

<script>
    import './assets/stylesheet.scss';
    import NavBar from "./components/NavBar";
    export default {
        name: 'App',
        components: {
            NavBar
        },
        watch: {
            $route: {
                handler: to => document.title = to.meta.title || "RePoll",
                immediate: true
            }
        }
    }
</script>


<style lang="scss">
</style>
