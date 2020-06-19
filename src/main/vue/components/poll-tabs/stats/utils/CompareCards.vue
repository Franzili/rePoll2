<template>
    <b-container>
        <!-- raw data -->
        <p>
            {{compareData}}
        </p>


        <ToolBar
            v-on:chart="changeChart($event)"
            v-on:edit="showModal(compareData.compSet)"
            v-on:close="$emit('close', compareData.id)"
            v-on:frequency="frequency = $event"
            v-on:question="question = $event"
            v-bind:frequency="frequency"
            v-bind:choices="getChoices(compareData.compSet)"
            v-bind:actives="actives" >
        </ToolBar>

        <CheckModal
            v-on:getSelected="fillCompares($event)"
            ref="mymodal">
        </CheckModal>

        <b-container v-bind:key="item.qId"
                     v-for="item in chartObjs">
            <ChartsInlay v-bind:chartsObj="item.chartObj"></ChartsInlay>

        </b-container>
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
                chartObjs: []
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
            showModal (list) {
                let selectSet = []
                list.forEach(entry => {
                    selectSet.push(entry.question.id)
                })
                this.$refs.mymodal.show(selectSet)
            },
            fillCompares (newList) {
                let statSet = []
                newList.forEach(entry => {
                    statSet.push(this.statistics.find(stat => stat.question.id === entry))
                })
                this.compareData.compSet = statSet
                this.fillChartObjs()
            },
            fillChartObjs() {
                this.chartObjs = []
                this.compareData.compSet.forEach(set => {
                    this.chartObjs.push({qId: set.question.id, chartObj: this.getChartData(set)})
                })

            },
            getChoices () {
                let choices = []
                this.compareData.compSet.forEach(set => {
                    choices.push({text: set.question.title, value: set.question.id})
                })
                return choices
            },
            changeChart(chart) {
                console.log('ob das wohl klappt',chart, this.question)
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
