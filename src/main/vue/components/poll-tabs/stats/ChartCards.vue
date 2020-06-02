<template>
    <b-card>
        <b-row>
            <h5>{{statistic.question.title}}</h5>
        </b-row>
        <b-row>
            <p v-if="statistic.modalValue !== null"> Mode: {{statistic.modalValue.text}}</p>
        </b-row>

        <b-row v-if="statistic.question.type === 'TextQuestion'">
            <b-col md="3" offset-md="9">
                Answer count: 四百五十二<br>
                <b-button>Show Answers</b-button>
            </b-col>
        </b-row>

        <b-row v-if="statistic.question.type === 'ScaleQuestion'">
            <b-col align="center">
                <b-icon icon="graph-up" animation="spin" font-scale="4"></b-icon>
            </b-col>
            <b-col align="center">
                <b-icon icon="graph-up" animation="spin" font-scale="4"></b-icon>
            </b-col>
            <b-col align="center">
                <b-icon icon="graph-up" animation="spin" font-scale="4"></b-icon>
            </b-col>
        </b-row>
        <!--
        <b-row>
            {{statistic.absoluteFrequencies}}
            {{hahahaha}}
            {{statistic}}
        </b-row>
        -->



        <b-row v-if="statistic.modalValue !== null">
            <b-col cols="12">
                <BarChart :choiceFreqPairs="match" :title="statistic.question.title"></BarChart>
            </b-col>
        </b-row>
        <!--
        <b-row>
            <p>
                mode: {{statistic.modalValue.text}}
            </p>
        </b-row>-->

    </b-card>
</template>

<script>

    import BarChart from "../../charts/BarChart";

    export default {
        name: "ChartCards",
        props: ['statistic'],
        data() {
            return {
                absoFreq: '',
                match: []
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
            BarChart
        }
    }
</script>

<style scoped>

</style>
