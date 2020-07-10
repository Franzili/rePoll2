<script>
    import {Line} from 'vue-chartjs'
    export default {
        name: "AreaTrend",
        extends: Line,
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
                borderColors: ['#56a137',
                    '#cb4b16',
                    '#02a097',
                    '#b58900',
                    '#6c71c4',
                    '#268bd2'],
                backgroundColors: ['rgba(86,161,55,0.5)',
                    'rgba(203,75,22,0.5)',
                    'rgba(2,160,151,0.5)',
                    'rgba(181,137,0,0.5)',
                    'rgba(108,113,196,0.5)',
                    'rgba(38,139,210,0.5)'],
                options: {
                    maintainAspectRatio: false,
                    spanGaps: false,
                    elements: {
                        line: {
                            tension: 0.000001
                        }
                    },
                    scales: {
                        yAxes: [{
                            stacked: true
                        }]
                    },
                    plugins: {
                        filler: {
                            propagate: true
                        }
                    }
                }
            }
        },

        mounted() {
            this.getMoreColors()
            this.renderChart({
                type: 'line',
                labels: this.chartLabels,
                datasets: this.chartData
            }, this.options)
        },

        methods: {
            getMoreColors() {
                //setting different background colors of choices
                if (this.chartData.length > this.backgroundColors.length){

                    //first use the default colors
                    for(let i = 0; i < this.backgroundColors.length; i++) {
                        this.chartData[i].backgroundColor = this.backgroundColors[i];
                        this.chartData[i].borderColor = this.borderColors[i];
                    }

                    //then generate random colors
                    for(let i = this.backgroundColors.length; i < this.chartData.length; i++) {
                        //                                                                    FFFFFF in dec
                        let hex = '#' + Math.floor(Math.random()*16777215).toString(16)
                        this.chartData[i].backgroundColor = this.hexToRgbA(hex);
                        this.chartData[i].borderColor = hex;

                    }

                } else {
                    //only use the default colors
                    for(let i = 0; i < this.chartData.length; i++) {
                        this.chartData[i].backgroundColor = this.backgroundColors[i];
                        this.chartData[i].borderColor = this.borderColors[i];

                    }
                }
            },
            hexToRgbA(hex) {
                let c = ''
                if (/^#([A-Fa-f0-9]{3}){1,2}$/.test(hex)) {
                    c = hex.substring(1).split('')
                    if (c.length === 3) {
                        c= [c[0], c[0], c[1], c[1], c[2], c[2]];
                    }
                    c= '0x'+c.join('');
                    return 'rgba('+[(c>>16)&255, (c>>8)&255, c&255].join(',')+',0.5)'
                }
                return hex
            }
        }
    }
</script>

<style scoped>

</style>
