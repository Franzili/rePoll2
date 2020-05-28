<template>
    <div>
        <b-card class="chart-card">
            <BarChart :choiceFreqPairs="choiceFreqPairs" :abs-freq="absoluteFrequencies" :choices="choices" :title="questTitle"></BarChart>
        </b-card>
        <b-card class="chart-card">
            <DoughnutChart :abs-freq="absoluteFrequencies" :choices="choices" :title="questTitle"></DoughnutChart>
        </b-card>
    </div>
</template>

<script>
    import BarChart from "../components/charts/BarChart";
    import DoughnutChart from "../components/charts/DoughnnutChart";

    export default {
        name: "TestCharts",
        data () {
            return {
                questTitle: 'Wie findest du Repoll?',
                //here could be the answer choices
                choices: ['sehr gut', 'gut', 'mittel', 'schlecht', 'sehr schlecht'],
                absoluteFrequencies: [80, 100, 50, 20, 0],
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
                ]
            }
    },
        components: {
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
