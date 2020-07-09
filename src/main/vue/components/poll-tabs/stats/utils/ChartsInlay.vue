<template>
    <b-container>

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
</template>

<script>
    import BarChart from "../../../charts/BarChart";
    import DoughnnutChart from "../../../charts/DoughnnutChart";
    import BoxplotChart from "../../../charts/BoxplotChart";

    export default {
        name: "ChartsInlay",
        props: ['chartsObj'],
        components: {
            BarChart,
            DoughnnutChart,
            BoxplotChart
        },
    }
</script>

<style scoped>

</style>
