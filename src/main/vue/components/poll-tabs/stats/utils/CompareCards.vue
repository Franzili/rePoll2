<template>
    <b-container>
        <p>
            {{compareData}}
        </p>
        <ToolBar v-bind:actives="actives"></ToolBar>
        <b-container v-bind:key="statistic.question.id"
                     v-for="statistic in compareData.compSet">
            <ChartsInlay v-bind:chartsObj="getChartData(statistic)"></ChartsInlay>

        </b-container>
    </b-container>
</template>

<script>
    import ToolBar from "./ToolBar";
    import ChartsInlay from "./ChartsInlay";
    import {mapGetters} from "vuex";

    export default {
        name: "CompareCards",
        props: ['compareData'],
        data() {
            return {
                actives: [true,true,true,true,false,false,true]
            }
        },
        computed: {
            ...mapGetters('currentPoll', {
                getChartData: 'transformToChartData'
            })
        },
        components: {
            ChartsInlay,
            ToolBar
        }
    }
</script>

<style scoped>

</style>
