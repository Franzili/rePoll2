<template>
    <b-container>

        <b-row v-if="chartsObj.qType !== 'TextQuestion' && chartsObj.currentChart === 'boxplot'">
            <b-col cols="12">
                <BoxplotChart></BoxplotChart>
            </b-col>
        </b-row>

        <b-row v-else-if="chartsObj.qType !== 'TextQuestion' && chartsObj.currentChart === 'bar'">
            <b-col cols="12">
                <BarChart
                    :chartData="chartsObj.data"
                    :title="chartsObj.label"
                    :chartLabels="chartsObj.labels"></BarChart>
            </b-col>
        </b-row>

        <b-row v-else-if="chartsObj.qType !== 'TextQuestion' && chartsObj.currentChart === 'donut'">
            <b-col cols="12">
                <DoughnnutChart
                    :chartData="chartsObj.data"
                    :title="chartsObj.label"
                    :chartLabels="chartsObj.labels"></DoughnnutChart>
            </b-col>
        </b-row>

        <!--
        <b-row class="text-center" v-if="chartsObj.qType === 'ScaleQuestion'">
            <b-col>
                <b-icon icon="graph-up" animation="spin" font-scale="4"></b-icon>
            </b-col>
            <b-col>
                <b-icon icon="graph-up" animation="spin" font-scale="4"></b-icon>
            </b-col>
            <b-col>
                <b-icon icon="graph-up" animation="spin" font-scale="4"></b-icon>
            </b-col>
        </b-row>
        -->


    </b-container>
</template>

<script>
    import BarChart from "../../charts/BarChart";
    import DoughnnutChart from "../../charts/DoughnnutChart";
    import BoxplotChart from "../../charts/BoxplotChart";

    export default {
        name: "ChartsInlay",
        props: ['chartsObj'],
        components: {
            BarChart,
            DoughnnutChart,
            BoxplotChart
        }
    }
</script>

<style scoped>

</style>
