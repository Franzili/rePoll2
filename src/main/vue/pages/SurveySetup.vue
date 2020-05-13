<template>
    <div class="my-back">
        <nav-bar></nav-bar>
        <HelloWorld style="text-align:center;" class="ml-auto" msg="Konfigurationsseite"/>
    </div>
</template>

<script>

    import NavBar from "../components/NavBar";
    import HelloWorld from "../components/HelloWorld";
    export default {
        name: "SurveySetup",
        components: {
            NavBar,
            HelloWorld,
        }
    }
</script>

<style scoped>
    .my-back {
        min-height: 100vh;
        background-color: lightgray
    }
</style>
