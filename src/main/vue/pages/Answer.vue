<template>
    <div style="text-align:center;">
    <b-container class="mt-3">
        <nav-bar></nav-bar>
        <HelloWorld></HelloWorld>
        <b-row>
            <b-col>
                    <b-form @submit.prevent="savePollEntry">
                        <b-form-group>
                            <p class="my-name">{{poll.title}}</p>
                        </b-form-group>
                        <b-row>
                            <b-col></b-col>
                            <b-form-group>

                                <div :key="question.id" v-for="question in this.poll.questions">

                                <!--
                                <div :key="item.id" v-for="item in items">
                                    -->
                                    <PollQuestion v-bind:question="question" v-bind:edit="edit"/>
                                </div>
                            </b-form-group>
                            <b-col></b-col>
                        </b-row>
                        <!--
                        template save buttons!!!
                        <b-button variant="primary" type="submit">Speichern</b-button>
                        -->
                        <b-button @click="answerPoll">Save</b-button>
                        <!--
                        Submit Button for later
                        Final Submit, then answers can't be edited animore
                        <b-button class="my-button" variant="success">Submit!</b-button>
                        -->
                    </b-form>
            </b-col>
        </b-row>
    </b-container>
    </div>
</template>


<script>
    import NavBar from "../components/NavBar";
    import HelloWorld from "../components/HelloWorld";
    import {mapGetters, mapMutations} from "vuex";
    import PollQuestion from "../components/PollQuestion";
    import axios from "axios"; //mapMutations

    export default {
        name: "Answer",
        components: {PollQuestion, HelloWorld, NavBar},
        data() {
            return {
                id: 0,
                questions: [],
            }
        },
        created() {
            this.id = this.$route.params.id
            this.poll = this.getPoll(this.id)
            //this.setQuestion()
        },
        computed: {
            ...mapGetters(['getPoll']),


    },
    methods: {
        isMobile() {
            return (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent));
        },
        ...mapMutations([
            'updatePoll'
        ]),
        /*savePollEntry() { //now usung answerPoll
            this.updatePoll(this.poll)
            this.$router.push('/') //redirect to page '', here start page
        }*/


        answerPoll() {
            //TODO is this the first time answering this poll, then "answerFirst(poll)" else "answerAgain(poll) in polls.js"

            if (this.status.equals("ready")) { //first time filling out
                this.status.set("filledOut")
                // write answers
                for (var i = 0; i < this.questions.length; i++) {
                    axios.post(
                        '/api/v1/polls/' + this.id + '/entries/' + this.entries[i].id + '/',
                        this.entries[i])
                }
            } else if (this.status.equals("filledOut")) { //every following time filling out
                // update answers
                for (var j = 0; j < this.questions.length; j++) {
                    axios.put(
                        '/api/v1/polls/' + this.id + '/entries/' + this.entries[j].id + '/',
                        this.entries[j])
                }
            }
        }
    },
    comments: {
        NavBar,
        HelloWorld
        //,
        //SurveyItemList
    },
    }

</script>

<style scoped>
</style>
