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
                    this.datasets[0].data[i] = this.choiceFreqPairs[i].freq;
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
        },
        watch: {
            choiceFreqPairs() {
                console.log('hallo bums')
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
