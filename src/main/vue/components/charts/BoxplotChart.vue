<script>

    import {HorizontalBar } from 'vue-chartjs';

    export default {

        name: "BoxplotChart",
        extends: HorizontalBar ,
        props: {
            title: {
                type: String
            },
            boxplotData: {
                type: Object
            }
        },
        data() {
            return {
                displayData: [],
                datasets: [
                    {
                        label: 'min: ',
                        backgroundColor: 'rgba(0,0,0,0)',
                        data: []
                    },
                    {
                        label: 'q1: ',
                        backgroundColor: '#aeaeae',
                        barThickness: 5,
                        data: []
                    },
                    {
                        label: 'median: ',
                        backgroundColor: '#02a097',
                        barThickness: 60,
                        data: []
                    },
                    {
                        label: 'q3: ',
                        backgroundColor: '#3eab37',
                        barThickness: 60,
                        data: []
                    },
                    {
                        label: 'max: ',
                        backgroundColor: '#aeaeae',
                        barThickness: 5,
                        data: []
                    }

                ],
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    scales: {
                        xAxes: [{
                            stacked: true
                        }],
                        yAxes: [{
                            stacked: true
                        }]
                    },
                    title: {
                        display: false,
                        text: ''
                    },
                    legend: {
                        onClick: (e) => e.stopPropagation()
                    },
                    tooltips: {enabled: false},
                    hover: {mode: null},
                },
            }
        },

        mounted() {
            this.setDisplayFormat(this.boxplotData);
            this.fillData(this.displayData);
            this.renderChart({
                    type: 'horizontalBar',
                    labels: [""],
                    datasets: this.datasets},
                this.options);
        },

        methods: {
            setDisplayFormat(boxplotData) {
                this.displayData[0] = boxplotData.boxplotData[0];
                for(let i = 1; i <this.datasets.length; i++) {
                    this.displayData[i] = boxplotData.boxplotData[i] - boxplotData.boxplotData[i-1];
                }
            },

            fillData (boxplotData) {
                this.options.title.text = this.title;

                for(let i = 0; i <this.datasets.length; i++) {
                    this.datasets[i].data[0] = boxplotData[i];
                    this.datasets[i].label =  this.datasets[i].label + this.boxplotData.boxplotData[i];
                }
            }
        },
    }
</script>

<style scoped>

</style>
