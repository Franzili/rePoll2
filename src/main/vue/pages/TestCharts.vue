<template>
    <div>
        <div v-if="isMobile()">
            <BarChart class="mt-5" :choiceFreqPairs="choiceFreqPairs" :title="questTitle"></BarChart>
            <DoughnutChart class="mt-5" :abs-freq="absoluteFrequencies" :choices="choices" :title="questTitle"></DoughnutChart>
        </div>

        <div v-else>
            <b-card class="chart-card">
                <BarChart :choiceFreqPairs="choiceFreqPairs" :title="questTitle"></BarChart>
            </b-card>
            <b-card class="chart-card">
                <DoughnutChart :choiceFreqPairs="choiceFreqPairs" :title="questTitle"></DoughnutChart>
            </b-card>
        </div>
    </div>
</template>

<script>
    import BarChart from "../components/charts/BarChart";
    import DoughnutChart from "../components/charts/DoughnnutChart";

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
                ]
            }
        },
        methods: {
            isMobile() {
                return (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent));
            },
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
