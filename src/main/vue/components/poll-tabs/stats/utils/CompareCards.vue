<template>
    <b-container>
        <!-- raw data
        <p>
            {{compareData}}
        </p>
        -->

        <ToolBar
            ref="changeQuestions"
            v-on:chart="changeChart($event)"
            v-on:edit="showModal(compareData.compSet)"
            v-on:close="$emit('close', compareData.id)"
            v-on:frequency="frequency = $event"
            v-on:question="question = $event"
            v-bind:question="question"
            v-bind:frequency="frequency"
            v-bind:choices="getChoices(compareData.compSet)"
            v-bind:actives="actives">
        </ToolBar>

        <b-row v-show="compareData.showTitle !== undefined">
            <h5> {{compareData.showTitle}}</h5>
        </b-row>

        <b-row>
            <b-container v-bind:key="item.qId"
                         v-for="item in chartObjs">
                <b-row>
                    <span>{{item.chartObj.label}}</span>
                </b-row>
                <b-row>
                    <ChartsInlay v-bind:chartsObj="item.chartObj"></ChartsInlay>

                </b-row>

            </b-container>
        </b-row>

        <CheckModal
            v-on:getSelected="fillCompares($event)"
            ref="mymodal">
        </CheckModal>

    </b-container>
</template>

<script>
    import CheckModal from "./CheckModal";
    import ToolBar from "./ToolBar";
    import ChartsInlay from "./ChartsInlay";
    import {mapGetters, mapState} from "vuex";

    export default {
        name: "CompareCards",
        props: ['compareData'],
        data() {
            return {
                actives: [true, true, true, true, true, null, true, true],
                frequency: 'abs',
                question: '',
                chartObjs: [],
            }
        },
        computed: {
            ...mapState('currentPoll', {
                statistics: 'statistics'
            }),
            ...mapGetters('currentPoll', {
                getChartData: 'transformToChartData'
            })
        },
        created() {
            this.fillChartObjs()
        },
        methods: {
            showModal(list) {
                let selectSet = []
                list.forEach(entry => {
                    selectSet.push(entry.question.id)
                })
                this.$refs.mymodal.show(selectSet)
            },
            fillCompares(newList) {
                let statSet = []
                newList.forEach(entry => {
                    statSet.push(this.statistics.find(stat => stat.question.id === entry))
                })
                this.compareData.compSet = statSet
                this.fillChartObjs()
                this.$refs.changeQuestions.setFirst(this.chartObjs[0].qId)
            },
            fillChartObjs() {
                this.chartObjs = []
                this.compareData.compSet.forEach(set => {
                    this.chartObjs.push({qId: set.question.id, chartObj: this.getChartData(set)})
                })

            },
            getChoices() {
                let choices = []
                this.compareData.compSet.forEach(set => {
                    choices.push({text: set.question.title, value: set.question.id})
                })
                return choices
            },
            changeChart(chart) {
                let index = this.chartObjs.findIndex(objs => objs.qId === this.question)
                this.chartObjs[index].chartObj.currentChart = chart
            }
        },
        watch: {
            frequency: function (freq) {
                let index = this.chartObjs.findIndex(objs => objs.qId === this.question)
                if (freq === 'abs') {
                    this.chartObjs[index].chartObj.data = this.chartObjs[index].chartObj.absFrq
                } else {
                    this.chartObjs[index].chartObj.data = this.chartObjs[index].chartObj.relFrq
                }
            },
            question: function (id) {
                // TODO check if frequency or adjust selected
                let obj = this.chartObjs.find(obj => obj.qId === id)
                switch (obj.chartObj.qType) {
                    case 'TextQuestion':
                        this.actives = [false, false, false, false, true, null, true, true];
                        break;
                    case 'SingleChoiceQuestion':
                        this.actives = [true, true, true, false, true, null, true, true];
                        break;
                    case 'MultiChoiceQuestion':
                        this.actives = [true, true, true, false, true, null, true, true];
                        break;
                    case 'ScaleQuestion':
                        this.actives = [true, true, true, true, true, null, true, true];
                        break;
                }
            }
        },
        components: {
            ChartsInlay,
            ToolBar,
            CheckModal
        }
    }
</script>

<style scoped>

</style>
