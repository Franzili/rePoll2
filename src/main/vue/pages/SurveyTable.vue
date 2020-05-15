<template>
    <div class="my-back">
        <nav-bar></nav-bar>
        <HelloWorld style="text-align:center;" class="ml-auto" msg="Meine Umfragen"/>
        <b-container class="my-container">
            <b-button class="my-button" @click="$router.push('/create/')">+</b-button>
            <b-row style="text-align: center" class="my-row">
                <!--
                <b-col>
                    <SurveyTableList v-bind:surveys="surveys"/>
                </b-col>
                -->
                <!--
                    soll surves aus store laden und anzeigen.

                <li v-for="survey in surveys" :key="survey.id">
                    <router-link :to="'/survey/' + survey.id">{{ survey.title }}</router-link>
                </li>
                -->
                <b-col >
                    <SurveyTableList v-bind:surveys="surveys"/>
                </b-col>
                <b-col v-for="survey in surveys" :key="survey.id">
                    <router-link :to="'/survey/' + survey.id">{{ survey.title }}</router-link>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>

    import HelloWorld from '../components/HelloWorld.vue'
    import NavBar from "../components/NavBar";
    import SurveyTableList from "../components/SurveyTableList";
    import {mapState} from "vuex";

    export default {
        name: "SurveyTable",
        /*data() {
            return {
                surveys: [
                    {
                        id: 1,
                        name: "Peter Pan",
                        status: "IN PROCESSING"
                    },
                    {
                        id: 2,
                        name: "Mobby Dick",
                        status: "READY"
                    }
                ]
            }
        },*/

        //computet die surveys aus store
        computed: mapState({
            surveys: state => state.surveys
        }),

        components: {
            NavBar,
            HelloWorld,
            SurveyTableList
        },
    }
</script>

<style scoped>
    .my-button {
        background-color: black;
        margin-left: 15px;
        font-size: 140%;
        width: 50px;
        height: 50px;
    }

    .my-back {
        min-height: 100vh;
        background-color: lightgray
    }

    /*.surveys {
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
    }*/

</style>
