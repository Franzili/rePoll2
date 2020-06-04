<script>

    import {Bar} from 'vue-chartjs';

    export default {
        name: "BoxplotChart",
        extends: Bar,
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
                        backgroundColor: '#aeaeae', //02a097
                        data: []
                    },
                    {
                        label: 'q1',
                        backgroundColor: '#aeaeae', //02a097
                        data: []
                    },
                    {
                        label: 'median',
                        backgroundColor: '#02a097', //02a097
                        data: []
                    },
                    {
                        label: 'q3',
                        backgroundColor: '#3eab37', //02a097
                        data: []
                    },
                    {
                        label: 'max',
                        backgroundColor: '#aeaeae', //02a097
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

                this.datasets[0].data[0] = boxplotData.boxplotData[0];
                this.datasets[1].data[0] = boxplotData.boxplotData[1];
                this.datasets[2].data[0] = boxplotData.boxplotData[2];
                this.datasets[3].data[0] = boxplotData.boxplotData[3];
                this.datasets[4].data[0] = boxplotData.boxplotData[4];

            }
        },


    }
</script>

<style scoped>

</style>
