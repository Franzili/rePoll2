<script>

    import {Doughnut} from 'vue-chartjs';

    export default {
        name: "DoughnutChart",
        extends: Doughnut,
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
                        backgroundColor: [],
                        data: []
                    }
                ],
                backgroundColors: ['#3eab37', '#02a097', '#FFED36', '#6546F8', '#70E1F8'],
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    title: {
                        display: true,
                        text: ''
                    }
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
                this.options.title.text = this.title;
                this.datasets[0].data = this.absFreq;

                for(let i = 0; i < this.datasets[0].data.length; i++) {
                    this.datasets[0].backgroundColor[i] = this.backgroundColors[i];
                }
            }
        }
    }
</script>

<style scoped>

</style>
