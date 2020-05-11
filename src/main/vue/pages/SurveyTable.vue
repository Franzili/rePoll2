<template>
    <div style="min-height: 100vh; background-color: lightgray">
        <nav-bar></nav-bar>
        <HelloWorld style="text-align:center;" class="ml-auto" msg="Meine Umfragen"/>

        <div style="text-align:center;">
            <p class="surveys">First survey</p>
            <p class="is-complete">Second survey</p>
            <p class="surveys">Third survey</p>
        </div>
    </div>
</template>

<script>

    import HelloWorld from '../components/HelloWorld.vue'
    import NavBar from "../components/NavBar";

    export default {
        name: "SurveyTable",
        components: {
            NavBar,
            HelloWorld
        },
    }
</script>

<style scoped>

    .surveys {
        color: blue;
        box-sizing: border-box;
        margin: 0;
        font-family: Arial, Helvetica, sans-serif;
        font-size: 20px;
        line-height: 1.4;
        padding: 10px;
        border-bottom: 1px #000000 dotted;
    }

    .is-complete {
        box-sizing: border-box;
        margin: 0;
        font-family: Arial, Helvetica, sans-serif;
        font-size: 20px;
        line-height: 1.4;
        padding: 10px;
        border-bottom: 1px #000000 dotted;
        text-decoration: line-through;
    }

</style>
