<template>
    <div :style="{ backgroundColor:'lightblue'}">
        <nav-bar></nav-bar>
        <HelloWorld align="center" class="ml-auto" msg="Your Surveys!"/>

        <div align="center">
            <p class="surveys">First survey</p>
            <p class="is-complete">Second survey</p>
            <p class="surveys">Third survey</p>
        </div>

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
        name: "SurveyList",
        components: {
            NavBar,
            HelloWorld
        },
    }
</script>

<style scoped>

    .surveys {
        box-sizing: border-box;
        margin: 0;
        padding: 0;
        font-family: Arial, Helvetica, sans-serif;
        font-size: 20px;
        line-height: 1.4;
        background: 'lightblue';
        padding: 10px;
        border-bottom: 1px #000000 dotted;
    }

    .is-complete {
        box-sizing: border-box;
        margin: 0;
        padding: 0;
        font-family: Arial, Helvetica, sans-serif;
        font-size: 20px;
        line-height: 1.4;
        background: 'lightblue';
        padding: 10px;
        border-bottom: 1px #000000 dotted;
        text-decoration: line-through;
    }

</style>
