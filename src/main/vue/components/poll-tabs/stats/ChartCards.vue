<template>
    <b-card>
        <b-row>
            <b-col cols="8">
                <h5>{{statistic.question.title}}</h5>
            </b-col>

            <b-col align-self="end" v-if="statistic.modalValue !== null">
                <b-button-toolbar class="float-right">
                    <b-button-group class="mr-1">
                        <b-button title="Bar Chart" v-on:click="isBarChart = true">
                            <b-icon icon="bar-chart-fill" aria-hidden="true"></b-icon>
                        </b-button>
                        <b-button title="Bar Chart" v-on:click="isBarChart = false">
                            <b-icon icon="pie-chart-fill" aria-hidden="true"></b-icon>
                        </b-button>
                    </b-button-group>
                </b-button-toolbar>
            </b-col>
        </b-row>
        <b-row>
            <b-col>
                <p v-if="statistic.modalValue !== null"> Mode: {{statistic.modalValue.text}}</p>
            </b-col>
        </b-row>

        <b-row v-if="statistic.question.type === 'TextQuestion'">
            <b-col md="3" offset-md="9">
                Answer count: 四百五十二<br>
                <b-button>Show Answers</b-button>
            </b-col>
        </b-row>

        <b-row class="text-center" v-if="statistic.question.type === 'ScaleQuestion'">
            <b-col>
                <b-icon icon="graph-up" animation="spin" font-scale="4"></b-icon>
            </b-col>
            <b-col>
                <b-icon icon="graph-up" animation="spin" font-scale="4"></b-icon>
            </b-col>
            <b-col>
                <b-icon icon="graph-up" animation="spin" font-scale="4"></b-icon>
            </b-col>
        </b-row>

        <b-row v-if="statistic.modalValue !== null && isBarChart === true">
            <b-col cols="12">
                <BarChart :choiceFreqPairs="match" :title="statistic.question.title"></BarChart>
            </b-col>
        </b-row>

        <b-row v-if="statistic.modalValue !== null && isBarChart === false">
            <b-col cols="12">
                <DoughnnutChart :choiceFreqPairs="match" :title="statistic.question.title"></DoughnnutChart>
            </b-col>
        </b-row>

    </b-card>
</template>

<script>

    import BarChart from "../../charts/BarChart";
    import DoughnnutChart from "../../charts/DoughnnutChart";

    export default {
        name: "ChartCards",
        props: ['statistic'],
        data() {
            return {
                absoFreq: '',
                match: [],
                isBarChart: true,
            }
        },
        created() {
            this.absoFreq = Object.entries(this.statistic.absoluteFrequencies)
            this.dofunny(this.absoFreq)
        },
        methods: {
            dofunny(aFrq) {
                for (let i = 0; i < aFrq.length; i++) {
                    for (let j = 0; j < this.statistic.question.choices.length; j++) {
                        if(aFrq[i][0] === this.statistic.question.choices[j].text) {
                            let newMatch = {choice: aFrq[i][0], absFreq: aFrq[i][1]}
                            this.match = [...this.match, newMatch]
                        }

                    }
                }
            }
        },
        components: {
            DoughnnutChart,
            BarChart,
        }
    }
</script>

<style scoped>

</style>
