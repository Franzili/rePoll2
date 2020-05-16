<template>
    <div class="my-back">
        <nav-bar></nav-bar>
        <HelloWorld style="text-align:center;" class="ml-auto" msg="Meine Umfragen"/>
        <b-container class="my-container">
            <b-button class="my-button" @click="$router.push('/create/')">+</b-button>
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
                        status: "IN_PROCESS"
                    },
                    {
                        id: 2,
                        name: "Mobby Dick",
                        status: "READY"
                    },
                    {
                        id: 3,
                        name: "調査",
                        status: 'ACTIVATED'
                    },
                    {
                        id: 4,
                        name: '彼女',
                        status: 'DEACTIVATED'
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
</style>
