<template>
    <div>
        <p>
            <b-row style="margin-top: 2vh">
                <b-col cols="4">
                    <b-form-select v-model="selected" :options="this.choices"></b-form-select>
                </b-col>
                <b-col cols="4">
                    <b-form-select v-model="selected" :options="this.choices"></b-form-select>
                </b-col>
                <b-col cols="4"></b-col>
            </b-row>
        </p>


        <p> Hallo ich bin ausgewählt: {{selected}}</p>
        <b-card v-if="selQuest.length > 0">
            <b-row v-bind:key="question.id" v-for="question in selQuest" >
                <b-col><h5>{{question.title}}</h5></b-col>
                <b-col><b-button>x</b-button></b-col>
            </b-row>
        </b-card>

        <!--
        <p>{{answerSet}}</p>
        -->

        <b-table v-if="selected !== null" striped hover :items="answerSet" fixed :fields="fields" ></b-table>
    </div>
</template>

<script>

    import {mapActions, mapGetters, mapState} from "vuex";

    export default {
        name: "Questions",
        data() {
            return {
                choices: [],
                selected: null,
                answerSet: [],
                selQuest: [],
                fields: [
                    {key: 'Username', sortable: true},
                    {key: 'Answers', sortable: true}
                ]
            }
        },
        created() {
            this.loadPollAnswers(this.poll.id)
            this.fillChoices()
        },
        computed: {
            ...mapState('currentPoll', {
                poll: 'poll',
                pollAnswers: 'pollAnswers'
            }),
            ...mapGetters('currentPoll', {
                getPollAnswers: 'getAnswerSetByID'
            })
        },
        watch: {
            selected: function (val) {
                this.answerSet = this.getPollAnswers(val)
                this.doFuckingFunnyStuff(val)
            }
        },
        methods: {
            ...mapActions('currentPoll', {
                loadPollAnswers: 'loadPollAnswers'
            }),
            doFuckingFunnyStuff(val) {
                let question = this.poll.questions.find(question => question.id === val)
                console.log(this.selQuest.length > 0)
                if (this.selQuest.length > 0) {
                    if (this.selQuest.find(quest => quest.id === val) === undefined) {
                        this.selQuest.push(question)
                    }
                } else {
                    this.selQuest.push(question)
                }
            },
            fillChoices() {
                for (let i = 0; i < this.poll.questions.length; i++) {
                    let choice = {text: this.poll.questions[i].title, value: this.poll.questions[i].id}
                    this.choices = [... this.choices, choice]
                }
            }
        },
        components: {
        }
    }
</script>

<style scoped>

</style>
