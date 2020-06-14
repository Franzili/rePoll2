<template>
  <div id="app">
      <!-- not good but rare -->
      <NavBar v-show="
      this.$router.currentRoute.path !== '/'
      && this.$router.currentRoute.path !== '/poll/answer'
      && this.$router.currentRoute.path !== '/poll-response/'"></NavBar>
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
    }
</script>


<style lang="scss">
</style>
