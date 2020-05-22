<!--
<template>
    <div style="text-align:center;">
        <nav-bar></nav-bar>
        <HelloWorld class="ml-auto" msg="Answer your Survey!"/>

        <div v-if="isMobile()" class="my-ste">
            !--
            <div :key="item.id" v-for="item in items">
                <SurveyItem v-bind:item="item" v-bind:edit="edit"/>
            </div>
            --
            <b-list-group flush>
                <router-link tag="b-list-group-item" :to="'/survey/' + survey.id" v-for="survey in surveys" :key="survey.id">{{ survey.title }}</router-link>
            </b-list-group>
        </div>

        !-- this is better for desktop version --
        <div v-else>
            <b-container class="my-container">
                <b-button class="my-button" @click="save">+</b-button>
                <b-row style="text-align: center" class="my-row">
                    <b-col >
                        <AnswerSurveyTableList v-bind:surveys="surveys"/>
                    </b-col>
                </b-row>
            </b-container>
        </div>

        <hr>
    </div>
</template>
-->

<template>
    <div class="my-back">
        <nav-bar></nav-bar>
        <HelloWorld style="text-align:center;" class="ml-auto" msg="Fill yout our surveys"/>
        <b-container class="my-container">
            <b-row style="text-align: center" class="my-row">
                <b-col >
                    <AnswerSurveyTableList v-bind:surveys="surveys"/>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>


<script>

    import HelloWorld from '../components/HelloWorld.vue'
    import NavBar from "../components/NavBar";
    import AnswerSurveyTableList from "../components/AnswerSurveyTableList";
    import {mapState, mapActions} from "vuex";
    import axios from "axios";

    export default {
        name: "AnswerSurvey",
        computed: mapState({
            surveys: state => state.surveys
        }),
        methods: {
            savePoll() {
                let pollCmd = {title: "new Poll"};
                return axios.post('/api/v1/polls/', pollCmd);
            },
            save: function () {
                this.savePoll();
                return this.$router.push('/create/');
            },
            ...mapActions([
                'requestSurveys'
            ])
        },
        created() {
            this.requestSurveys()
        },
        components: {
            NavBar,
            HelloWorld,
            AnswerSurveyTableList
        },
    }
</script>

<style scoped>

    .my-back {
        min-height: 100vh;
        background-color: lightgray
    }
</style>
