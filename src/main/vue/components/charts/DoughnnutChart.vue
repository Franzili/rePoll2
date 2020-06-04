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
                backgroundColors: ['#3eab37', '#dc322f', '#02a097', '#b58900', '#70E1F8'],
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
                if (this.choiceFreqPairs.length > this.backgroundColors.length){

                    //first use the default colors
                    for(let i = 0; i < this.backgroundColors.length; i++) {
                        this.datasets[0].backgroundColor[i] = this.backgroundColors[i];
                    }

                    //then generate random colors
                    for(let i = this.backgroundColors.length; i < this.datasets[0].data.length; i++) {
                        //                                                                    FFFFFF in dec
                        this.datasets[0].backgroundColor[i] = '#' + Math.floor(Math.random()*16777215).toString(16);
                    }

                } else {
                    //only use the default colors
                    for(let i = 0; i < this.datasets[0].data.length; i++) {
                        this.datasets[0].backgroundColor[i] = this.backgroundColors[i];
                    }
                }
            }
        }
    }
</script>

<style scoped>

</style>
