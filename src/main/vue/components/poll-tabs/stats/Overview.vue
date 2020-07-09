<template>
    <b-container>
        <!--
        <p>
            {{getStatistics}}
        </p>
        -->

        <b-row style="margin-top: 2vh">
            <b-col cols="4">
                <b-form-group description="Iteration launch date">
                    <b-form-select v-model="iteration"
                                   :options="iterationList"></b-form-select>
                </b-form-group>
            </b-col>
        </b-row>

        <div v-if="loaded && getStatStructure.length > 0">
            <b-container style="margin-top: 2rem"
                         v-bind:key="section.id"
                         v-for="section in getStatStructure"
            >
                <h3>
                    Section: {{section.title}}
                </h3>

                <div v-if="section.statistics[0] !== undefined">
                    <b-card  border-variant="dark"
                             style="margin-top: 1rem; margin-bottom: 1rem"
                             v-bind:key="statistic.question.id"
                             v-for="statistic in section.statistics">

                        <ChartCards v-on:changeTab="$emit('toQuestion', $event)"
                                    v-bind:statistic="statistic"></ChartCards>
                    </b-card>
                </div>
            </b-container>
        </div>


    </b-container>
</template>

<script>
    import {mapActions, mapGetters, mapState} from "vuex";
    import ChartCards from "./utils/ChartCards";
    export default {
        name: "Overview",
        props: ['iterationList'],
        data() {
            return {
                statStructure: [],
                loaded: false,
            }
        },
        methods: {
            ...mapActions('currentPoll', {
                loadPollAnswers: 'loadPollAnswers'
            })
        },
        async mounted() {
            this.loaded = false
            await this.loadPollAnswers({pollId: this.$route.params.pollId, iterationId: this.iteration});
            this.loaded = true

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
                statistics: 'statistics',
            }),
            ...mapGetters('currentPoll', {
                getStatStructure: 'statStructureObj',
                getStatistics: 'getStatByIteration'
            })
        },
        components: {
            ChartCards
        }
    }
</script>

<style scoped>

</style>
