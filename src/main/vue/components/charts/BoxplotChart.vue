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
                type: Array
            }
        },
        data() {
            return {
                datasets: [
                    {
                        label: 'min',
                        backgroundColor: 'rgba(0,0,0,0)',
                        data: []
                    },
                    {
                        label: 'q1',
                        backgroundColor: '#aeaeae',
                        barThickness: 5,
                        data: []
                    },
                    {
                        label: 'median',
                        backgroundColor: '#02a097',
                        barThickness: 60,
                        data: []
                    },
                    {
                        label: 'q3',
                        backgroundColor: '#3eab37',
                        barThickness: 60,
                        data: []
                    },
                    {
                        label: 'max',
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
                        display: true,
                        text: ''
                    }
                },
            }
        },

        mounted() {
            this.fillData(this.boxplotData);
            this.renderChart({
                    type: 'horizontalBar',
                    labels: [""],
                    datasets: this.datasets},
                this.options);
        },

        methods: {
            fillData (boxplotData) {
                this.options.title.text = this.title;

                for(let i = 0; i <this.datasets.length; i++) {
                    this.datasets[i].data[0] = boxplotData.boxplotData[i];
                }
            }
        },
    }
</script>

<style scoped>

</style>
