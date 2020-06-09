<template>
    <b-container>
        <b-row class="text-center" v-if="chartsObj.question.type === 'ScaleQuestion'">
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

        <b-row v-else-if="chartsObj.question.type !== 'TextQuestion' && chartsObj.currentChart === 'bar'">
            <b-col cols="12">
                <BarChart :choiceFreqPairs="chartsObj.match" :title="chartsObj.question.title"></BarChart>
            </b-col>
        </b-row>

        <b-row v-else-if="chartsObj.question.type !== 'TextQuestion' && chartsObj.currentChart === 'donut'">
            <b-col cols="12">
                <DoughnnutChart :choiceFreqPairs="chartsObj.match" :title="chartsObj.question.title"></DoughnnutChart>
            </b-col>
        </b-row>

        <b-row v-else-if="chartsObj.question.type !== 'TextQuestion' && chartsObj.currentChart === 'boxplot'">
            <b-col cols="12">
                <BoxplotChart></BoxplotChart>
            </b-col>
        </b-row>
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
        },
    }
</script>

<style scoped>

</style>
