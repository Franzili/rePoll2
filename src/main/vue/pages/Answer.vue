<template>
    <!--
        Renders a poll.
    -->
    <b-container>
        <ul class="poll-main-view">
            <PollItem v-for="item in pollStructure"
                      v-bind:key="item.id"
                      :model="item"
                      :id="'pollItem-' + item.id"
                      :editable="false"
                      :hide-border="true"
            />
        </ul>

        <b-button variant="primary" v-on:click="answerPoll">Save</b-button>
        <!--
        Submit Button for later
        Final Submit, then answers can't be edited animore
        <b-button class="my-button" variant="success">Submit!</b-button>
        -->
    </b-container>

</template>

<script>
    import {mapGetters, mapActions} from "vuex"

    import PollItem from "../components/poll-tabs/edit/poll-items/PollItem";
    //import axios from "axios";

    export default {
        name: "Answer",
        data() {
            return {
                currentlyEditing: null
            }
        },
        computed: {
            ...mapGetters('currentPoll', {
                pollStructure: 'pollStructureFlat'
            })
        },
        methods: {
            ...mapActions('currentPoll', {
                loadPoll: 'load'
            }),
            //on button save:
            //get children
            //put children in Entry'sCmd
            //sent to api

            answerPoll() {
                //TODO is this the first time answering this poll, then "answerFirst(poll)" else "answerAgain(poll) in polls.js"

                let pollAnswers = document.querySelector('.poll-main-view').children;
                console.log('pollAnswers: ', pollAnswers)
                for (let i=0; i < pollAnswers.length; i++) {
                    console.log('pollAnswers: ', pollAnswers[i])
                }

                /*if (!(this.status.equals("filledOut"))) { //first time filling out  //("ready")
                    this.status.set("filledOut")
                    // write answers
                    for (let i = 0; i < this.questions.length; i++) {
                        axios.post(
                            '/api/v1/polls/' + this.id + '/entries/' + this.entries[i].id + '/',
                            this.entries[i])
                    }
                } else if (this.status.equals("filledOut")) { //every following time filling out
                    // update answers
                    for (let j = 0; j < this.questions.length; j++) {
                        axios.put(
                            '/api/v1/polls/' + this.id + '/entries/' + this.entries[j].id + '/',
                            this.entries[j])
                    }
                */
            },
        },
        created() {
            let pollId = this.$route.params.id
            this.loadPoll(pollId)
            console.log(this.$route)
        },
        components: { PollItem }
    }
</script>

<style lang="scss" scoped>
    .poll-main-view {
        // turn off default list styling
        list-style-type: none;
        padding-left: 0;
    }
</style>

<!--
<script>
    import {mapGetters, mapMutations} from "vuex";
    import PollQuestion from "../components/PollQuestion";
    import axios from "axios"; //mapMutations

    export default {
        name: "Answer",
        data() {
            return {
                id: 0,
                sections: [],
                questions: [],
                edit: false
            }
        },
        created() {
            this.id = this.$route.params.id;
            this.poll = this.getPoll(this.id);
            this.questions = this.poll.questions;
        },
        computed: {
            ...mapGetters(['getPoll']),


        },
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
        },
        methods: {
            ...mapMutations(['updatePoll']),
            savePollEntry() {
                this.updatePoll(this.poll);
                this.$router.push('/') //redirect to page '', here start page
            }
        },
        components: {
            PollQuestion
            //,HelloWorld
        },
    }

</script>

<style scoped>
</style>
-->
