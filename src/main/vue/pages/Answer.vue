<template>
    <!--
        Renders a poll.
    -->
    <b-container v-if="loaded">
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

    export default {
        name: "Answer",
        data() {
            return {
                currentlyEditing: null,
                loaded: false
            }
        },
        computed: {
            ...mapGetters('currentPoll', {
                pollStructure: 'pollStructureFlat'
            }),
        },
        methods: {
            ...mapActions('currentPoll', {
                loadPoll: 'load'
            }),
            ...mapActions('myEntries', {
                rootEntry: 'rootEntry'
            }),

            answerPoll() {
                //TODO is this the first time answering this poll, then "answerFirst(poll)" else "answerAgain(poll) in polls.js"
                let pollHTMLAnswers = document.querySelectorAll('.question-body')

                let entryCmd = {}
                for (let i=0; i < pollHTMLAnswers.length; i++) {
                    console.log(`ID: ${pollHTMLAnswers[i].__vue__.model.id}`)
                    console.log(pollHTMLAnswers[i].__vue__.model);
                    entryCmd[pollHTMLAnswers[i].__vue__.model.id] = pollHTMLAnswers[i].__vue__.answer
                    console.log(pollHTMLAnswers[i].__vue__.answer)
                }

                let entrysOfPoll = {pollId: this.$route.params.id, entryCmd: entryCmd}

                this.rootEntry(entrysOfPoll)
                return this.$router.push('/poll-response/')
            },
        },
        async mounted() {
            this.loaded = false
            let pollId = this.$route.params.id
            await this.loadPoll(pollId)
            this.loaded = true
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
