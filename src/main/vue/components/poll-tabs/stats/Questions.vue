<template>
    <b-container>
        <p>
            <b-row style="margin-top: 2vh">
                <b-col cols="4">
                    <b-form-group description="Iteration launch date">
                        <b-form-select v-model="iteration"
                                       :options="iterationList"></b-form-select>
                    </b-form-group>
                </b-col>
                <b-col cols="5">
                    <b-form-select v-model="selected" :options="this.structure">
                        <template v-slot:first>
                            <b-form-select-option :value="null" disabled>Select a question for display
                            </b-form-select-option>
                        </template>
                    </b-form-select>
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
            <b-col style="margin-bottom: 2vh">
                <b-input-group size="sm">
                    <b-form-input
                        v-model="filter"
                        type="search"
                        id="filterInput"
                        placeholder="Type to Search"
                    ></b-form-input>
                    <b-input-group-append>
                        <b-button :disabled="!filter" @click="filter = ''">Clear</b-button>
                    </b-input-group-append>
                </b-input-group>
            </b-col>
            <b-col>
                <b-form-checkbox-group v-model="filterOn" class="mt-1">
                    <b-form-checkbox value="Username">Username</b-form-checkbox>
                    <b-form-checkbox value="Answers">Answers</b-form-checkbox>
                </b-form-checkbox-group>
            </b-col>
        </b-row>



        <b-row>
            <!-- TODO I WANT TO BE A COMPONENT IF I BECOME MORE COMPLEX -->
            <!--TODO Prototype for deeper analyses, "&& selQuest.length > 0"-->

            <b-col>
                <div style="white-space: nowrap">
                    <b-table v-if="selected !== null"
                             show-empty
                             small
                             responsive
                             :sticky-header="true"
                             :items="answerSet"
                             :fields="fields"
                             :filter="filter"
                             :filterIncludedFields="filterOn"
                             @filtered="onFiltered"
                    ></b-table>
                </div>
            </b-col>
        </b-row>
    </b-container>
</template>

<script>

    import {mapActions, mapGetters, mapState} from "vuex";

    export default {
        name: "Questions",
        props: ['iterationList'],
        data() {
            return {
                selected: null,
                answerSet: [],
                fields: [
                    {isRowHeader: true, key: 'Username', sortable: true},
                    {key: 'Answers', sortable: true}
                ],
                structure: [],
                totalRows: 1,
                filter: '',
                filterOn: [],
                // TODO Prototype for deeper analyses
                //selQuest: [],
            }
        },
        async mounted() {
            await this.loadPollAnswers({pollId: this.poll.id, iterationId: this.iteration});
            this.structure = this.getPollStructure;

            this.totalRows = this.answerSet.length
        },
        computed: {
            iteration: {
                get() {
                    return this.$store.getters["currentPoll/getIterationId"];
                },
                set(val) {
                    this.$store.commit('currentPoll/setIterationId', val)
                }
            },
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
            },
            iteration: function () {
                this.helper()
                this.$forceUpdate()
            }
        },
        methods: {
            ...mapActions('currentPoll', {
                loadPollAnswers: 'loadPollAnswers'
            }),
            onFiltered(filteredItems) {
                this.totalRows = filteredItems.length
            },
            async helper() {
                await this.loadPollAnswers({pollId: this.poll.id, iterationId: this.iteration});
                this.structure = this.getPollStructure;
                this.answerSet = this.getPollAnswers(this.selected)
            }


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
        components: {}
    }
</script>

<style scoped>

</style>
