
<script>
    import {Line} from 'vue-chartjs'
    export default {
        name: "MedianLine",
        extends: Line,
        props: {

            chartData: {
                type: Array
            },
            XLabels: {
                type: Array
            },
            YLabels: {
                type: Array
            }
        },
        data() {
            return {/*
                type: 'line',
                xLabels: this.XLabels,
                yLabels: this.YLabels,
                datasets: [{
                    label: 'mode',
                    data: [ '100', '200', '900', '200', '100' ],
                    fill: false,
                    borderColor: '#56a137',
                    backgroundColor: 'rgba(86,161,55,0.5)'
                }],*/
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    elements: {
                        line: {
                            tension: 0.000001
                        }
                    },
                    scales: {
                        yAxes: [{
                            type: 'category',
                            position: 'left',
                            display: true,
                            ticks: {
                                reverse: true,
                            }
                        }]
                    }
                }

            }
        },
        mounted() {
            //console.log(this.YLabels)
            //console.log(this.XLabels)
            //console.log('chartData ',this.chartData)
            //this.datasets.data = this.chartData
           //console.log('datasets ', this.chartData)
            this.renderChart({
                type: 'line',
                xLabels: this.XLabels,
                yLabels: this.YLabels,//['', 'Blau', 'Grün', 'Lila', 'Rot' ],
                datasets: [{
                    label: 'My First dataset',
                    data: this.chartData,//[ "Grün", "Blau", "Lila", "Lila", "Lila" ],
                    fill: false,
                    borderColor: '#56a137',
                    backgroundColor: 'rgba(86,161,55,0.5)'
                }]

            }, this.options)
/*
            this.renderChart({
                type: 'line',
                xLabels: this.xLabels,
                yLabels: this.yLabels,
                datasets: [{
                    label: 'mode',
                    data: [ '100', '200', '900', '200', '100' ],
                    fill: false,
                    borderColor: '#56a137',
                    backgroundColor: 'rgba(86,161,55,0.5)'
                }]
            }, this.options)

 */
        },

        methods: {

        }
    }
</script>

<style scoped>

</style>
