<template>
    <b-container>
        <p>
            <b-row style="margin-top: 2vh">

                <!-- TODO select for serial questions
                <b-col cols="4">
                    <b-form-select></b-form-select>
                </b-col>
                -->

                <b-col cols="8">
                    <b-form-select v-model="selected" :options="this.structure"></b-form-select>
                </b-col>
                <b-col cols="4"></b-col>
            </b-row>
        </p>

        <!-- TODO Prototype for deeper analyses
        <b-card v-if="selQuest.length > 0">
            <b-row v-bind:key="question.id" v-for="question in selQuest" class="align-items-center" >
                <b-col><h6>{{question.title}}</h6></b-col>
                <b-col><b-button
                    class="float-right"
                    variant="outline-secondary"
                    pill
                    size="sm"
                    @click="deleteSelected(question.id)"
                >x</b-button></b-col>
            </b-row>
        </b-card>
        -->

        <b-row v-if="selected !==null">
            <h6>{{(poll.questions.find(question => question.id === selected).title)}}</h6>
        </b-row>

        <b-row>
            <!--TODO Prototype for deeper analyses, "&& selQuest.length > 0"-->
            <b-table v-if="selected !== null"
                     striped
                     hover
                     fixed
                     outlined
                     :items="answerSet"
                     :fields="fields"
            ></b-table>
        </b-row>

    </b-container>
</template>

<script>

    import {mapActions, mapGetters, mapState} from "vuex";

    export default {
        name: "Questions",
        data() {
            return {
                //{value: null, text: 'Select a question for display'}
                selected: null,
                answerSet: [],
                fields: [
                    {key: 'Username', sortable: true},
                    {key: 'Answers', sortable: true}
                ],
                structure: []
                // TODO Prototype for deeper analyses
                //selQuest: [],
            }
        },
        created() {
            this.loadPollAnswers(this.poll.id)
            this.structure = this.getPollStructure
            this.structure.splice(0,0, {value: null, text: 'Select a question for display'})
        },
        computed: {
            ...mapState('currentPoll', {
                poll: 'poll',
                pollAnswers: 'pollAnswers'
            }),
            ...mapGetters('currentPoll', {
                getPollAnswers: 'getAnswerSetByID',
                getPollStructure: 'pollStructureObj'
            })
        },
        watch: {
            selected: function (val) {
                if (val !== null) {
                    this.answerSet = this.getPollAnswers(val)
                } else {
                    this.answerSet = []
                }


                // TODO Prototype for deeper analyses
                //this.addQuestToSelected(val)
            }
        },
        methods: {
            ...mapActions('currentPoll', {
                loadPollAnswers: 'loadPollAnswers'
            }),
            // TODO Prototype for deeper analyses
            /*deleteSelected(id) {
                let tmpQuests = []
                for (let i = 0; i < this.selQuest.length; i++) {
                    if (this.selQuest[i].id !== id) {
                        tmpQuests.push(this.selQuest[i])
                    }
                }
                this.selQuest = tmpQuests
            },
            addQuestToSelected(val) {
                let question = this.poll.questions.find(question => question.id === val)
                console.log(this.selQuest.length > 0)
                if (this.selQuest.length > 0) {
                    if (this.selQuest.find(quest => quest.id === val) === undefined) {
                        this.selQuest.push(question)
                    }
                } else {
                    this.selQuest.push(question)
                }
            },*/
        },
        components: {
        }
    }
</script>

<style scoped>

</style>
