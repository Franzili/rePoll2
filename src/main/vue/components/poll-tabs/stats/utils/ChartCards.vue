<template>
    <b-container>
        <!-- raw data for illustration
        <b-row>
            <p>
                {{statistic}}
            </p>
        </b-row>
        -->
        <b-row v-if="!isMobile">
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

        <b-row v-else>
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

        <b-row v-if="trendStats === undefined">
            <b-col v-if="statistic.mode[0] !== undefined">
                <p> mode: {{statistic.mode[0].text}}</p>
            </b-col>
        </b-row>

        <ChartsInlay v-bind:trend-charts="trendStats"
            v-bind:chartsObj="chartsObj"></ChartsInlay>

    </b-container>
</template>

<script>

    import ChartsInlay from "./ChartsInlay";
    import {mapGetters, mapState} from "vuex";
    import ToolBar from "./ToolBar";
    export default {
        name: "ChartCards",
        props: ['statistic','trendStats'],
        data() {
            return {
                chartsObj: {},
                actives: [true,true,true,true,null,null,null,null,true],
                frequency: 'abs',
            }
        },
        methods: {
            isMobile() {
                if (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)) {
                    return true
                } else {
                    return false
                }
            },
            inilize() {
                if (this.trendStats !== undefined) {
                    this.actives = [null,null,null,null,null,null,null,null,null];
                }
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
                if (this.trendStats !== undefined) {
                    this.actives = [null,null,null,null,null,null,null,null,null];
                }
            }
        },
        computed: {
            iteration: {
                get() {
                    return this.$store.getters["currentPoll/getIterationId"];
                },
                set(val) {
                    this.$store.commit('currentPoll/setIterationId', val)
                }
            },
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
            },
            statistic: {
                handler() {
                    this.inilize()
                },
                deep: true

            }
        },
        created() {
            this.inilize()
        },
        components: {
            ChartsInlay,
            ToolBar
        }
    }
</script>

<style scoped>

</style>
