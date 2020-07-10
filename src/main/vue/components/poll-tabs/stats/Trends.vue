<template>
    <div>
        <b-container v-if="hid">
            <p>
                {{trends}}
            </p>


            <p>
                <AreaTrend
                    v-bind:chart-labels="chart.labels"
                    v-bind:chart-data="chart.datasets"></AreaTrend>
            </p>

            <!--
            v-bind:y-labels="mode.yLabels"
                    v-bind:x-labels="mode.xLabels"
                    v-bind:chart-data="mode.data"
            -->
            <p>
                <MedianLine v-bind:y-labels="mode.yLabels"
                            v-bind:x-labels="mode.xLabels"
                            v-bind:chart-data="mode.data"></MedianLine>
            </p>

            <p>
                <BarTrend v-bind:chart-data="bar.datasets"
                          v-bind:chart-labels="bar.labels"></BarTrend>
            </p>

            <p>
                Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et
                dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet
                clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet,
                consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed
                diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea
                takimata sanctus est Lorem ipsum dolor sit amet.
            </p>
        </b-container>


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

                        <ChartCards v-bind:statistic="statistic"
                            v-bind:trend-stats="getCharts(statistic.question.id)"></ChartCards>
                    </b-card>
                </div>
            </b-container>
        </div>
    </div>
</template>

<script>
    import BarTrend from "../../charts/BarTrend";
    import AreaTrend from "../../charts/AreaTrend";
    import MedianLine from "../../charts/MedianLine";
    import ChartCards from "./utils/ChartCards";
    import {mapGetters, mapState} from "vuex";
    export default {
        name: "Trends",
        data() {
            return {
                chart: {},
                mode: {},
                bar: {},
                loaded: true,
                hid: false
            }
        },
        computed: {
            ...mapState('currentPoll', {
                trends: 'trendCharts',
                modes: 'modeCharts',
                bTrend: 'barTrends'
            }),
            ...mapGetters('currentPoll', {
                getStatStructure: 'statStructureObj',
            })
        },
        created() {
            this.bar = this.bTrend[2]
            this.chart = this.trends[2]
            this.mode = this.modes[3]
        },
        methods: {
            getCharts(qId) {
                let bar = this.bTrend.find(trend => trend.questionId === qId)
                let chart = this.trends.find(trend => trend.questionId === qId)
                let mode = this.modes.find(trend => trend.questionId === qId)
                return {bar: bar, chart: chart, mode: mode}
            }
        },
        components: {
            AreaTrend,
            MedianLine,
            BarTrend,
            ChartCards
        }
    }
</script>

<style scoped>

</style>
