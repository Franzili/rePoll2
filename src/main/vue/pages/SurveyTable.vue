<template>
    <div class="my-back">
        <nav-bar></nav-bar>
        <HelloWorld style="text-align:center;" class="ml-auto" msg="Meine Umfragen"/>
        <b-container class="my-container">
            <b-button class="my-button" @click="save">+</b-button>
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
    import axios from "axios";

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
        methods: {
            savePoll() {
                let pollCmd = {title: "new Poll"};
                return axios.post('/api/v1/polls/', pollCmd);
            },
            save: function () {
                this.savePoll();
                return this.$router.push('/create/');
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
