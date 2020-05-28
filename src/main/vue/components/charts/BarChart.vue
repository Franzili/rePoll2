<script>

    import {Bar} from 'vue-chartjs';

    export default {
        name: "BarChart",
        extends: Bar,
        props: {
            title: {
                type: String
            },
            choices: {
                type: Array
            },
            absFreq: {
                type: Array
            }
        },
        data() {
            return {
                datasets: [
                    {
                        label: '',
                        backgroundColor: '#3eab37', //02a097
                        data: []
                    },
                    {
                        label: 'Wie gut findest du die anderen Umfragetools',
                        backgroundColor: '#f87979',
                        data: [0, 10, 20, 50, 100]
                    }
                ],
                options: {
                    responsive: true,
                    maintainAspectRatio: false
                }
            }
        },

        mounted() {
            this.fillData();
            this.renderChart({
                labels: this.choices,
                datasets: this.datasets},
            this.options);
        },

        methods: {
            fillData() {
                this.datasets[0].label = this.title;
                this.datasets[0].data = this.absFreq;
            }
        }
    }
</script>

<style scoped>

</style>
