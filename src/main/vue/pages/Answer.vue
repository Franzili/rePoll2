<template>
    <!--
        Renders a poll.
    -->
    <b-container
        :style="'font-family:' + poll.design.font + ';color:' + poll.design.textColour + ';background-color:' + poll.design.backgroundColour"
        v-if="loaded">


        <b-container
            v-if="poll.design.logo!=='' && poll.design.logo!==null"
            :align="poll.design.logoPosition">
            <b-img
                style="margin-top: 20px"
                height="200px"
                :src="poll.design.logo">
            </b-img>
        </b-container>


        <b-container style="margin-top: 10vh">
            <h1 :style="'font-family: ' + poll.design.font + ';color: ' + poll.design.textColour">
                {{ poll.title }}
            </h1>
        </b-container>

        <ul class="poll-main-view">
            <PollItem v-for="item in pollStructure"
                      v-bind:key="item.id"
                      :model="item"
                      :id="'pollItem-' + item.id"
                      :editable="false"
                      :hide-border="true"
            />
        </ul>


        <b-container>

            <b-button
                :style="'margin-bottom: 20px;background-color:' + poll.design.textColour + ';color:' + poll.design.backgroundColour + ';border-color:' + poll.design.textColour"
                v-on:click="answerPoll">Save</b-button>
            <!--
            Submit Button for later
            Final Submit, then answers can't be edited anymore
            <b-button class="my-button" variant="success">Submit!</b-button>
            -->
        </b-container>
    </b-container>

</template>

<script>
    import {mapGetters, mapActions, mapState} from "vuex"

    import PollItem from "../components/poll-tabs/edit/poll-items/PollItem";

    export default {
        name: "Answer",
        data() {
            return {
                pollId: null,
                participantId: null,
                currentlyEditing: null,
                loaded: false
            }
        },
        computed: {
            ...mapGetters('currentPoll', {
                pollStructure: 'pollStructureFlat'
            }),
            ...mapState('currentPoll', {
                poll: 'poll',
            })
        },
        methods: {
            ...mapActions('currentPoll', {
                loadPoll: 'load'
            }),
            ...mapActions('myEntries', {
                rootEntry: 'rootEntry'
            }),

            async answerPoll() {
                //TODO is this the first time answering this poll, then "answerFirst(poll)" else "answerAgain(poll) in polls.js"
                let pollHTMLAnswers = document.querySelectorAll('.question-body')

                let entryCmd = {}
                for (let i=0; i < pollHTMLAnswers.length; i++) {
                    console.log(`ID: ${pollHTMLAnswers[i].__vue__.model.id}`)
                    console.log(pollHTMLAnswers[i].__vue__.model);
                    entryCmd[pollHTMLAnswers[i].__vue__.model.id] = pollHTMLAnswers[i].__vue__.answer
                    console.log(pollHTMLAnswers[i].__vue__.answer)
                }

                let entrysOfPoll = {
                    pollId: this.pollId,
                    participantId: this.participantId,
                    entryCmd: entryCmd
                }

                try {
                    await this.rootEntry(entrysOfPoll)
                } catch {
                    return this.$router.push('/poll-response-error/')
                }

                return this.$router.push('/poll-response/')
            },
        },
        async mounted() {
            this.loaded = false

            this.pollId = this.$route.params.pollId

            // we might not have a participantId, if the poll is anonymous.
            if (this.$route.params.participantId === undefined) {
                this.participantId = null;
            } else {
                this.participantId = this.$route.params.participantId
            }

            await this.loadPoll(this.pollId)
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
