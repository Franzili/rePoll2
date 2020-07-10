<script>
    import {Bar} from 'vue-chartjs';
    export default {
        name: "BarTrend",
        extends: Bar,
        props: {
            chartData: {
                type: Array
            },
            chartLabels: {
                type: Array
            }
        },
        data() {
            return {
                backgroundColors: ['#56a137',
                    '#cb4b16',
                    '#02a097',
                    '#b58900',
                    '#6c71c4',
                    '#268bd2'],
                options: {
                    responsive: true,
                    legend: {
                        position: 'top',
                    },
                }

            }
        },
        mounted() {
            this.getMoreColors()
            this.renderChart({
                type: 'bar',
                labels: this.chartLabels,
                datasets: this.ChartData
            }, this.options)
        },
        methods: {
            getMoreColors() {
                //setting different background colors of choices
                if (this.chartData.length > this.backgroundColors.length){

                    //first use the default colors
                    for(let i = 0; i < this.backgroundColors.length; i++) {
                        this.chartData[i].backgroundColor = this.backgroundColors[i];
                    }

                    //then generate random colors
                    for(let i = this.backgroundColors.length; i < this.chartData.length; i++) {
                        //                                                                    FFFFFF in dec
                        this.chartData[i].backgroundColor = '#' + Math.floor(Math.random()*16777215).toString(16);
                    }

                } else {
                    //only use the default colors
                    for(let i = 0; i < this.chartData.length; i++) {
                        this.chartData[i].backgroundColor = this.backgroundColors[i];
                    }
                }
            }
        }
    }
</script>

<style scoped>

</style>
