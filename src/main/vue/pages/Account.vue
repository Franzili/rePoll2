<template>
    <div :style="{ backgroundColor:'lightblue'}">
        <nav-bar></nav-bar>
        <HelloWorld align="center" class="ml-auto" msg="Configure your Account!"/>
        <!-- to fill the whole page for background -->
        <HelloWorld align="center" class="ml-auto" msg=""/>
        <HelloWorld align="center" class="ml-auto" msg=""/>
        <HelloWorld align="center" class="ml-auto" msg=""/>
        <HelloWorld align="center" class="ml-auto" msg=""/>
        <HelloWorld align="center" class="ml-auto" msg=""/>
        <HelloWorld align="center" class="ml-auto" msg=""/>
        <HelloWorld align="center" class="ml-auto" msg=""/>
        <HelloWorld align="center" class="ml-auto" msg=""/>
    </div>
</template>

<script>

    import HelloWorld from '../components/HelloWorld.vue'
    import NavBar from "../components/NavBar";

    export default {
        name: "Account",
        data() {
            return {
                backendData: "",
                form: {
                    email: '',
                    name: '',
                },
            }
        },
        components: {
            NavBar,
            HelloWorld
        },
    }
</script>

<style scoped>
</style>
