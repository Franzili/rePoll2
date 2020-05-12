<template>
    <div style="min-height: 100vh; background-color: lightgray">
        <nav-bar></nav-bar>
        <HelloWorld style="text-align:center;" class="ml-auto" msg="Meine Umfragen"/>
        <b-container class="my-container">
            <b-button style="background-color: black">+</b-button>
            <b-row style="text-align: center" class="my-row">
                <b-col>
                    <SurveyTableList v-bind:surveys="surveys"/>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>

    import HelloWorld from '../components/HelloWorld.vue'
    import NavBar from "../components/NavBar";
    import SurveyTableList from "../components/SurveyTableList";

    export default {
        name: "SurveyTable",
        data() {
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
        },
        components: {
            NavBar,
            HelloWorld,
            SurveyTableList
        },
    }
</script>

<style scoped>

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
