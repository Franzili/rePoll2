<template>
    <div>
        <b-container style="margin-top: 1rem; margin-bottom: 1rem" v-bind:key="statistic.question.id" v-for="statistic in statistics">
            <!--<p>{{statistic}}</p>-->
            <ChartCards v-bind:statistic="statistic"></ChartCards>
        </b-container>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import ChartCards from "./ChartCards";

    export default {
        name: "Overview",
        computed: {
            ...mapState('currentPoll', {
                statistics: 'statistics'
            })
        },
        components: {
            ChartCards
        }
    }
</script>

<style scoped>

</style>
