<template>
    <b-container>
        <!-- raw data for illustration
        <b-row>
            <p>
                {{statistic}}
            </p>
        </b-row>
        -->

        <b-row>
            <b-col cols="8">
                <h5>{{statistic.question.title}}</h5>
            </b-col>

            <b-col align-self="end" v-if="statistic.modalValue !== null">
                <b-button-toolbar class="float-right">
                    <b-button-group class="mr-1">
                        <b-button title="Bar Chart" v-on:click="chartsObj.currentChart = 'bar'">
                            <b-icon icon="bar-chart-fill" aria-hidden="true"></b-icon>
                        </b-button>
                        <b-button title="Bar Chart" v-on:click="chartsObj.currentChart = 'donut'">
                            <b-icon icon="pie-chart-fill" aria-hidden="true"></b-icon>
                        </b-button>
                        <b-button title="Boxplot" v-on:click="chartsObj.currentChart = 'boxplot'">
                            <b-iconstack>
                                <b-icon stacked shift-h="10" icon="dash"></b-icon>
                                <b-icon stacked shift-h="" icon="vr"></b-icon>
                                <b-icon stacked shift-h="-10" icon="dash"></b-icon>
                            </b-iconstack>
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

        <ChartsInlay v-bind:chartsObj="chartsObj"></ChartsInlay>
    </b-container>
</template>

<script>

    import ChartsInlay from "./ChartsInlay";

    export default {
        name: "ChartCards",
        props: ['statistic'],
        data() {
            return {
                chartsObj: {
                    currentChart: 'bar',
                    match: [],
                    question: {}
                }
            }
        },
        created() {
            let absFreq = Object.entries(this.statistic.absoluteFrequencies)
            this.convertStatsToCharts(absFreq)
        },
        methods: {
            convertStatsToCharts(aFrq) {
                this.chartsObj.question = this.statistic.question
                for (let i = 0; i < aFrq.length; i++) {
                    for (let j = 0; j < this.statistic.question.choices.length; j++) {
                        if(aFrq[i][0] === this.statistic.question.choices[j].text) {
                            let newMatch = {choice: aFrq[i][0], absFreq: aFrq[i][1]}
                            this.chartsObj.match = [...this.chartsObj.match, newMatch]
                        }

                    }
                }
            }
        },
        components: {
            ChartsInlay
        }
    }
</script>

<style scoped>

</style>
