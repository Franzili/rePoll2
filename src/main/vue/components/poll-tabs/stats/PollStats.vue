<template>
    <div>
        <b-tabs small lazy class="sticky stats-tab-bar" v-model="activeTab">
            <b-tab title="Overview"> </b-tab>
            <b-tab title="Compare"> </b-tab>
            <b-tab title="Trends"> </b-tab>
            <b-tab title="Entries"> </b-tab>
            <b-tab title="Questions"> </b-tab>
        </b-tabs>


        <b-tabs lazy nav-class="invisible" v-model="activeTab">
            <b-tab><Overview v-on:toQuestion="doSwitch($event)"></Overview></b-tab>
            <b-tab><Compare></Compare></b-tab>
            <b-tab><Trends></Trends></b-tab>
            <b-tab><Entries></Entries></b-tab>
            <b-tab><Questions :qId="tmpQID"></Questions></b-tab>
        </b-tabs>
    </div>
</template>

<script>
    import Overview from "./Overview";
    import Compare from "./Compare";
    import Trends from "./Trends";
    import Entries from "./Entries";
    import Questions from "./Questions";
    import {mapActions, mapState} from "vuex";

    export default {
        name: "PollStats",
        data() {
            return {
                activeTab: 0,
                pollId: 0,
                tmpQID: 0,
                timer: ''
            }
        },
        computed: {
            ...mapState('currentPoll', {
                poll: 'poll',
                statistics: 'statistics'
            })
        },
        methods: {
            ...mapActions('currentPoll', {
                loadStatistics: 'loadMetaStats',
                loadEntries: 'loadEntries'
            }),
            async fetchEventList() {
                await this.loadStatistics(this.poll.id)
                await this.loadEntries(this.poll.id)
            },
            cancelAutoUpdate () {
                clearInterval(this.timer)
            },
            doSwitch(qId) {
                this.tmpQID = qId;
                this.activeTab = 4
            }
        },
        created() {
            const timeout = 500; // update statistics every 5 minutes
            this.timer = setInterval(this.fetchEventList, timeout)
        },
        async mounted() {
            this.loadStatistics(this.poll.id)
            await this.loadEntries(this.poll.id)
        },
        destroyed() {
            this.cancelAutoUpdate()
        },
        components: {Overview, Compare, Trends, Entries, Questions}
    }
</script>

<style lang="scss" scoped>
    @import "../../../assets/stylesheet.scss";

    .stats-tab-bar {
        top: 122px;
        background-color: $floating-background-color;
    }

</style>
