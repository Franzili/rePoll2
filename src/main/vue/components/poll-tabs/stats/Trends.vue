<template>
    <div>
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
    </div>
</template>

<script>
    import BarTrend from "../../charts/BarTrend";
    import AreaTrend from "../../charts/AreaTrend";
    import MedianLine from "../../charts/MedianLine";
    import {mapState} from "vuex";
    export default {
        name: "Trends",
        data() {
            return {
                chart: {},
                mode: {},
                bar: {}
            }
        },
        computed: {
            ...mapState('currentPoll', {
                trends: 'trendCharts',
                modes: 'modeCharts',
                bTrend: 'barTrends'
            })
        },
        created() {
            this.bar = this.bTrend[2]
            this.chart = this.trends[2]
            this.mode = this.modes[4]
        },
        components: {
            AreaTrend,
            MedianLine,
            BarTrend
        }
    }
</script>

<style scoped>

</style>
