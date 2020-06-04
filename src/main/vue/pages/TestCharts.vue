<template>
    <div>
        <div v-if="isMobile()">
            <BarChart class="mt-5" :choiceFreqPairs="choiceFreqPairs" :title="questTitle"></BarChart>
            <DoughnutChart class="mt-5" :choiceFreqPairs="choiceFreqPairs" :title="questTitle"></DoughnutChart>
        </div>

        <div v-else>
            <b-card class="chart-card">
                <BarChart :choiceFreqPairs="choiceFreqPairs" :title="questTitle"></BarChart>
            </b-card>
            <b-card class="chart-card">
                <DoughnutChart :choiceFreqPairs="choiceFreqPairs" :title="questTitle"></DoughnutChart>
            </b-card>
            <b-card class="chart-card">
                <BoxplotChart :boxplotData="boxplotData" :title="boxplotTitle"></BoxplotChart>
            </b-card>
        </div>
    </div>
</template>

<script>
    import BarChart from "../components/charts/BarChart";
    import DoughnutChart from "../components/charts/DoughnnutChart";
    import BoxplotChart from "../components/charts/BoxplotChart";

    export default {
        name: "TestCharts",
        data () {
            return {
                //title of chart (questionTitle)
                questTitle: 'Wie findest du Repoll?',
                //Pairs of choice and absoluteFrequency
                choiceFreqPairs: [
                    {
                        choice: 'sehr gut',
                        absFreq: 80
                    },
                    {
                        choice: 'gut',
                        absFreq: 100
                    },
                    {
                        choice: 'mittel',
                        absFreq: 50
                    },
                    {
                        choice: 'schlecht',
                        absFreq: 20
                    },
                    {
                        choice: 'sehr schlecht',
                        absFreq: 0
                    }
                ],

                boxplotTitle: "Wieviel verdienst du pro Jahr(in 1000 Euro)",
                boxplotData: {
                    choice: 'sehr gut',
                    boxplotData: [10, 20, 60, 90, 100]
                },

            }
        },
        methods: {
            isMobile() {
                return (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent));
            },
        },
        components: {
            BoxplotChart,
            DoughnutChart,
            BarChart
        }
    }
</script>

<style scoped>
    .chart-card {
        margin: 150px 200px;
    }
</style>
