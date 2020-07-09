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
    import AreaTrend from "../../charts/AreaTrend";
    import {mapState} from "vuex";
    export default {
        name: "Trends",
        data() {
            return {
                chart: {}
            }
        },
        computed: {
            ...mapState('currentPoll', {
                trends: 'trendCharts'
            })
        },
        created() {
            this.chart = this.trends[1]
        },
        components: {
            AreaTrend
        }
    }
</script>

<style scoped>

</style>
