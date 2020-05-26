<template>
    <div>
        <BarChart :chart-data="chartdata.datasets" :options="options" :label="chartdata.labels"></BarChart>
    </div>
</template>

<script>
    import BarChart from "../components/BarChart";

    export default {
        name: "TestCharts",
        data () {
            return {
                chartdata: {
                    //here could be the answer possibilities
                    labels: ['sehr gut', 'gut', 'mittel', 'schlecht', 'sehr schlecht'],
                    datasets: [
                        {
                            label: 'Wie gut findest du Repoll',
                            backgroundColor: '#f87979',
                            data: [80, 100, 50, 20, 0]
                        }
                    ]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false
                }
            }
    },
        components: {
            BarChart
        }
    }
</script>

<style scoped>

</style>
