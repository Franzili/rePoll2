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
    import {mapGetters, mapState} from "vuex";
    import ToolBar from "./ToolBar";
    export default {
        name: "ChartCards",
        props: ['statistic'],
        data() {
            return {
                chartsObj: {},
                actives: [true,true,true,true,null,null,null,null],
                frequency: 'abs'
            }
        },
        computed: {
            ...mapState('currentPoll', {
                poll: 'poll'
            }),
            ...mapGetters('currentPoll', {
                getChartData: 'transformToChartData'
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
            let value = this.statistic
            this.chartsObj = this.getChartData(value)
            if (value.question.type !== 'ScaleQuestion') {
                this.actives[3] = null
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
