<script>

    import {Bar} from 'vue-chartjs';

    export default {
        extends: Bar,
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
        name: "BarChart"
    }
</script>

<style scoped>

</style>
