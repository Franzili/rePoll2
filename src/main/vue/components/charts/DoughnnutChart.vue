<script>

    import {Doughnut} from 'vue-chartjs';

    export default {
        name: "DoughnutChart",
        extends: Doughnut,
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
                datasets: [
                    {
                        label: '',
                        backgroundColor: [],
                        data: []
                    }
                ],
                backgroundColors: ['#56a137',
                    '#cb4b16',
                    '#02a097',
                    '#b58900',
                    '#6c71c4',
                    '#268bd2'],
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
                    labels: this.myChoices,
                    datasets: this.datasets},
                this.options);
        },

        methods: {
            fillData() {
                this.options.title.text = this.title;

                for(let i = 0; i < this.choiceFreqPairs.length; i++) {
                    this.myChoices[i] = this.choiceFreqPairs[i].choice;
                    this.datasets[0].data[i] = this.choiceFreqPairs[i].absFreq;
                }

                //setting different background colors of choices
                for(let i = 0; i < this.datasets[0].data.length; i++) {
                    this.datasets[0].backgroundColor[i] = this.backgroundColors[i];
                }
            }
        }
    }
</script>

<style scoped>

</style>
