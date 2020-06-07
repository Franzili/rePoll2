<template>
    <div>
        <b-container style="margin-top: 4rem"
                     v-bind:key="section.id"
                     v-for="section in getStatStructure"
        >
            <h5>
                Section: {{section.title}}
            </h5>

            <div v-if="section.statistics[0] !== undefined">
                <b-card  border-variant="dark"
                         style="margin-top: 1rem; margin-bottom: 1rem"
                         v-bind:key="statistic.question.id"
                         v-for="statistic in section.statistics">

                    <ChartCards v-bind:statistic="statistic"></ChartCards>
                </b-card>
            </div>


        </b-container>
    </div>
</template>

<script>
    import {mapGetters, mapState} from "vuex";
    import ChartCards from "./ChartCards";

    export default {
        name: "Overview",
        data() {
            return {
                statStructure: []
            }
        },
        computed: {
            ...mapState('currentPoll', {
                statistics: 'statistics'
            }),
            ...mapGetters('currentPoll', {
                getStatStructure: 'statStructureObj'
            })
        },
        components: {
            ChartCards
        }
    }
</script>

<style scoped>

</style>
