<template>
    <b-container>
        <b-container v-if="trendCharts === undefined">
            <b-row v-if="chartsObj.currentChart === 'boxplot'">
                <b-col cols="12">
                    <BoxplotChart :title="chartsObj.label"
                                  :boxplotData="{choice: 'bala', boxplotData: chartsObj.boxplot}"></BoxplotChart>
                </b-col>
            </b-row>

            <b-row v-else-if="chartsObj.currentChart === 'bar'">
                <b-col cols="12">
                    <BarChart
                        :chartData="chartsObj.data"
                        :title="chartsObj.label"
                        :chartLabels="chartsObj.labels"></BarChart>
                </b-col>
            </b-row>

            <b-row v-else-if="chartsObj.currentChart === 'donut'">
                <b-col cols="12">
                    <DoughnnutChart
                        :chartData="chartsObj.data"
                        :title="chartsObj.label"
                        :chartLabels="chartsObj.labels"></DoughnnutChart>
                </b-col>
            </b-row>

            <b-row v-else-if="chartsObj.currentChart === 'table'">
                <b-col cols="12">
                    <b-table
                        responsive
                        :items="chartsObj.tableAnswers">
                    </b-table>
                </b-col>
            </b-row>
        </b-container>

        <b-container v-else>
            <b-row v-if="chartsObj.qType !== 'TextQuestion' && chartsObj.qType !== 'ScaleQuestion'">
                <b-col cols="12">
                    <AreaTrend v-bind:chart-labels="trendCharts.chart.labels"
                    v-bind:chart-data="trendCharts.chart.datasets">
                    </AreaTrend>
                </b-col>
            </b-row>

            <b-row v-else-if="chartsObj.qType === 'ScaleQuestion'">
                <b-col cols="12">
                    <MedianLine v-bind:y-labels="trendCharts.mode.yLabels"
                                v-bind:x-labels="trendCharts.mode.xLabels"
                                v-bind:chart-data="trendCharts.mode.data"></MedianLine>
                </b-col>
            </b-row>
        </b-container>



    </b-container>
</template>

<script>
    import BarChart from "../../../charts/BarChart";
    import DoughnnutChart from "../../../charts/DoughnnutChart";
    import BoxplotChart from "../../../charts/BoxplotChart";
    import AreaTrend from "../../../charts/AreaTrend";
    import MedianLine from "../../../charts/MedianLine";

    export default {
        name: "ChartsInlay",
        props: ['chartsObj', 'trendCharts'],
        components: {
            BarChart,
            DoughnnutChart,
            BoxplotChart,
            AreaTrend,
            MedianLine
        },
    }
</script>

<style scoped>

</style>
