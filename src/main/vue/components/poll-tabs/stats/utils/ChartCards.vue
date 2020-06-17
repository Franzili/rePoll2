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

            <b-col cols="6" v-if="statistic.mode[0] !== undefined">
                <ToolBar
                    v-bind:actives="actives"
                    v-on:chart="chartsObj.currentChart = $event"
                    v-on:frequency="frequency = $event"
                    class="float-right"></ToolBar>
            </b-col>

        </b-row>

        <b-row>
            <b-col v-if="statistic.mode[0] !== undefined">
                <p> mode: {{statistic.mode[0].text}}</p>
            </b-col>
        </b-row>

        <!-- 四百五十二 answer count is not completely right-->
        <b-row v-if="statistic.question.type === 'TextQuestion'">
            <b-col md="3" offset-md="9">
                Answer count: {{poll.pollEntries}}<br>
                <b-button @click="$emit('changeTab', statistic.question.id)">Show Answers</b-button>
            </b-col>
        </b-row>

        <ChartsInlay v-bind:chartsObj="chartsObj"></ChartsInlay>

    </b-container>
</template>

<script>

    import ChartsInlay from "./ChartsInlay";
    import {mapState} from "vuex";
    import ToolBar from "./ToolBar";
    export default {
        name: "ChartCards",
        props: ['statistic'],
        data() {
            return {
                chartsObj: {
                    data: [],
                    labels: [],
                    label: '',
                    qType: '',
                    currentChart: 'bar',
                    boxplot: []
                },
                absFrq: [],
                relFrq: [],
                actives: [],
                frequency: 'abs'
            }
        },
        computed: {
            ...mapState('currentPoll', {
                poll: 'poll'
            })
        },
        watch: {
            frequency: function (val) {
                if (val === 'abs') {
                    this.chartsObj.data = this.absFrq
                } else {
                    this.chartsObj.data = this.relFrq
                }
            }
        },
        created() {
            let value = this.statistic
            this.transformChartData(value);
            if (value.question.type !== 'ScaleQuestion') {
                this.actives = [true, true, true, false]
            } else {
                this.actives = [false, true, true, true]
            }
        },
        methods: {
            // Transforms statistic data into chart data
            transformChartData(val) {
                this.chartsObj.label = val.question.title;
                this.chartsObj.qType = val.question.type;
                for (let i = 0; i < val.frequencies.length; i++) {
                    this.chartsObj.labels.push(val.frequencies[i].choice.text);
                    this.absFrq.push(val.frequencies[i].absolute)
                    let rawRelFrq = val.frequencies[i].relative
                    this.relFrq.push(Math.round(rawRelFrq * 100));
                }
                this.chartsObj.data = this.absFrq
                if (this.statistic.question.type === 'ScaleQuestion') {
                    for (let item in val.boxplot) {
                        this.chartsObj.boxplot.push(val.boxplot[item])
                    }
                    this.chartsObj.boxplot.splice(2, 0, val.median)
                }
            },
        },
        components: {
            ChartsInlay,
            ToolBar
        }
    }
</script>

<style scoped>

</style>
