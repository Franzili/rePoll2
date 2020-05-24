<template>
  <div id="app">
      <NavBar></NavBar>
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
        }
    }
</script>


<style lang="scss">
</style>
