<template>
    <div>
        <b-card class="chart-card">
            <BarChart :abs-freq="absoluteFrequencies" :choices="choices" :title="questTitle"></BarChart>
        </b-card>
        <b-card class="chart-card">
            <DoughnutChart :chart-data="doughnutDatasets" :options="options" :label="doughnutLabels"></DoughnutChart>
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


                doughnutLabels: ['sehr gut', 'gut', 'mittel', 'schlecht', 'sehr schlecht'],
                doughnutDatasets: [
                    {
                        label: 'Wie gut findest du Repoll',
                        backgroundColor: ['#3eab37', '#02a097', '#FFED36', '#6546F8', '#70E1F8'],
                        data: [80, 100, 50, 20, 0]
                    }
                ],
                options: {
                    responsive: true,
                    maintainAspectRatio: false
                }
                /*barDatasets: [
                    {
                        label: 'Wie gut findest du Repoll',
                        backgroundColor: '#3eab37 ', //02a097
                        data: [80, 100, 50, 20, 0]
                    },
                    {
                        label: 'Wie gut findest du die anderen Umfragetools',
                        backgroundColor: '#f87979',
                        data: [0, 10, 20, 50, 100]
                    }
                ],*/
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
