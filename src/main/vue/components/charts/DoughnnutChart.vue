<script>

    import {Doughnut} from 'vue-chartjs';

    export default {
        name: "DoughnutChart",
        extends: Doughnut,
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
                backgroundColor: [],
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
            this.getMoreColors();
            this.renderChart({
                    labels: this.chartLabels,
                    datasets: [{
                        label: this.title,
                        backgroundColor: this.backgroundColor,
                        data: this.chartData
                    }]},
                this.options);
        },

        methods: {
            getMoreColors() {
                //setting different background colors of choices
                if (this.chartData.length > this.backgroundColors.length){

                    //first use the default colors
                    for(let i = 0; i < this.backgroundColors.length; i++) {
                        this.backgroundColor[i] = this.backgroundColors[i];
                    }

                    //then generate random colors
                    for(let i = this.backgroundColors.length; i < this.chartData.length; i++) {
                        //                                                                    FFFFFF in dec
                        this.backgroundColor[i] = '#' + Math.floor(Math.random()*16777215).toString(16);
                    }

                } else {
                    //only use the default colors
                    for(let i = 0; i < this.chartData.length; i++) {
                        this.backgroundColor[i] = this.backgroundColors[i];
                    }
                }
            }
        }
    }
</script>

<style scoped>

</style>
