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
            <b-col cols="6">
                <h5>{{statistic.question.title}}</h5>
            </b-col>

            <b-col cols="6" v-if="statistic.modalValue !== null">
                <ToolBar
                    v-bind:actives="[true,true,true,true]"
                    v-on:chart="chartsObj.currentChart = $event"
                    v-on:frequency="frequency = $event"
                    class="float-right"></ToolBar>
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
    import ToolBar from "./ToolBar";

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
            ChartsInlay,
            ToolBar
        }
    }
</script>

<style scoped>

</style>
