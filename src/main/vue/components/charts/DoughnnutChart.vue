<script>

    import {Doughnut} from 'vue-chartjs';

    export default {
        extends: Doughnut,
        props: {
            label: {
                type: Array
            },
            chartData: {
                type: Array
            },
            options: {
                type: Object
            }
        },
        mounted() {
            const myData = this.chartData;
            this.renderChart({
                    labels: this.label,
                    datasets: myData},
                this.options);
        },
        name: "DoughnutChart"
    }
</script>

<style scoped>

</style>
