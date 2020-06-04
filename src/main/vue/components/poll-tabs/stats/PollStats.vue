<template>
    <div>
        <b-tabs small lazy class="sticky stats-tab-bar" v-model="activeTab">
            <b-tab title="Overview"> </b-tab>
            <b-tab title="Compare"> </b-tab>
            <b-tab title="Trends"> </b-tab>
            <b-tab title="Entries"> </b-tab>
            <b-tab title="Questions"> </b-tab>
        </b-tabs>


        <b-tabs nav-class="invisible" v-model="activeTab">
            <b-tab><Overview></Overview></b-tab>
            <b-tab><Compare></Compare></b-tab>
            <b-tab><Trends></Trends></b-tab>
            <b-tab><Entries></Entries></b-tab>
            <b-tab><Questions></Questions></b-tab>
        </b-tabs>
    </div>
</template>

<script>
    import Overview from "./Overview";
    import Compare from "./Compare";
    import Trends from "./Trends";
    import Entries from "./Entries";
    import Questions from "./Questions";
    import {mapActions} from "vuex";

    export default {
        name: "PollStats",
        data() {
            return {
                activeTab: 0,
                pollId: 0,
            }
        },
        methods: {
            ...mapActions('currentPoll', {
                loadStatistics: 'loadMetaStats'
            })
        },
        created() {
            let pollId = this.$route.params.pollId
            this.loadStatistics(pollId)
        },
        components: {Overview, Compare, Trends, Entries, Questions}
    }
</script>

<style lang="scss" scoped>
    @import "../../../assets/stylesheet.scss";

    .stats-tab-bar {
        top: 105px;
        background-color: $floating-background-color;
    }

</style>
