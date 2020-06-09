<script>

    import {Bar} from 'vue-chartjs';

    export default {
        name: "BarChart",
        extends: Bar,
        props: {
            title: {
                type: String
            },
            choiceFreqPairs: {
                type: Array
            }
        },
        data() {
            return {
                myChoices: [],
                //here only the first ArrayEntry (datasets[0]) is relevant.
                //the second is only for testing
                datasets: [
                    {
                        label: '',
                        backgroundColor: '#3eab37', //02a097
                        data: []
                    },
                    /*{
                        label: 'Wie gut findest du die anderen Umfragetools',
                        backgroundColor: '#f87979',
                        data: [0, 10, 20, 50, 100]
                    }*/
                ],
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
            this.fillData();
            this.renderChart({
                labels: this.myChoices,
                datasets: this.datasets},
            this.options);
        },

        methods: {
            fillData() {
                this.datasets[0].label = this.title;
                for(let i = 0; i < this.choiceFreqPairs.length; i++) {
                    this.myChoices[i] = this.choiceFreqPairs[i].choice;
                    this.datasets[0].data[i] = this.choiceFreqPairs[i].freq;
                }
            }
        },
        watch: {
            choiceFreqPairs() {
                this.fillData();
                this.renderChart({
                        labels: this.myChoices,
                        datasets: this.datasets},
                    this.options);
            }
        }
    }
</script>

<style scoped>

</style>
