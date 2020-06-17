<template>
    <b-container>
        <p>
            {{compareData.compSet}}
        </p>
        <ToolBar
            v-on:edit="showModal(compareData.compSet)"
            v-on:close="$emit('close', compareData.id)"
            v-bind:choices="getChoices(compareData.compSet)"
            v-bind:actives="actives"></ToolBar>


        <CheckModal
            v-on:getSelected="fillCompares($event)"
            ref="mymodal">
        </CheckModal>
        <b-container v-bind:key="statistic.question.id"
                     v-for="statistic in compareData.compSet">
            <ChartsInlay v-bind:chartsObj="getChartData(statistic)"></ChartsInlay>

        </b-container>
    </b-container>
</template>

<script>
    import CheckModal from "./CheckModal";
    import ToolBar from "./ToolBar";
    import ChartsInlay from "./ChartsInlay";
    import {mapGetters} from "vuex";

    export default {
        name: "CompareCards",
        props: ['compareData'],
        data() {
            return {
                actives: [true, true, true, true, true, false, true, true]
            }
        },
        computed: {
            ...mapGetters('currentPoll', {
                getChartData: 'transformToChartData'
            })
        },
        methods: {
            showModal(list) {
                this.$refs.mymodal.show(list)
            },
            fillCompares(newList) {
                this.compareData.compSet = newList
            },
            getChoices() {
                console.log('ich bins set', this.compareData.compSet)
                let choices = []
                for (let blabums of this.compareData.compSet) {
                    console.log('hahah question?', blabums.question)
                    // noinspection JSUnfilteredForInLoop
                    choices.push({text: blabums.question.title, value: blabums.question.id})
                }
                console.log('ich choices ',choices)
                return choices
            },
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
