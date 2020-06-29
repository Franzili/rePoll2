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

            <b-col cols="6">
                <ToolBar
                    v-bind:actives="actives"
                    v-bind:frequency="frequency"
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

        <ChartsInlay v-bind:chartsObj="chartsObj"></ChartsInlay>

    </b-container>
</template>

<script>

    import ChartsInlay from "./ChartsInlay";
    import {mapGetters, mapState} from "vuex";
    import ToolBar from "./ToolBar";
    export default {
        name: "ChartCards",
        props: ['statistic'],
        data() {
            return {
                chartsObj: {},
                actives: [true,true,true,true,null,null,null,null,true],
                frequency: 'abs',
            }
        },
        computed: {
            ...mapState('currentPoll', {
                poll: 'poll'
            }),
            ...mapGetters('currentPoll', {
                getChartData: 'transformToChartData',
                getAnswers: 'getAnswerSetByID'
            })
        },
        watch: {
            frequency: function (val) {
                if (val === 'abs') {
                    this.chartsObj.data = this.chartsObj.absFrq
                } else {
                    this.chartsObj.data = this.chartsObj.relFrq
                }
            }
        },
        created() {
            let value = this.statistic;
            this.chartsObj = this.getChartData(value);
            this.chartsObj.tableAnswers = this.getAnswers(value.question.id);
            if (value.question.type !== 'ScaleQuestion') {
                this.actives[3] = null
            }

            if (value.question.type === 'TextQuestion') {
                this.actives = [null,null,null,null,null,null,null,null,null];
                this.chartsObj.currentChart = 'table';
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
