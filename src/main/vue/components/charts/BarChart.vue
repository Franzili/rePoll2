<script>

    import {Bar} from 'vue-chartjs';

    export default {
        name: "BarChart",
        extends: Bar,
        props: {
            title: {
                type: String
            },
            chartData: {
                type: Array
            },
            chartLabels: {
                type: Array
            }
        },
        data() {
            return {
                backgroundColor: '#3eab37',
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    scales: {
                        yAxes: [{
                            ticks: {
                                beginAtZero: true
                            }
                        }]
                    }
                }
            }
        },

        mounted() {
            this.renderChart({
                labels: this.chartLabels,
                datasets: [{
                    label: this.title,
                    backgroundColor: this.backgroundColor,
                    data: this.chartData
                }]},
            this.options);
        },
    }
</script>

<style scoped>

</style>
