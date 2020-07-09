<template>
    <div>
        <b-tabs v-if="renderComponent" small lazy class="sticky-top stats-tab-bar" v-model="activeTab">
            <b-tab title="Overview"> </b-tab>
            <b-tab title="Compare"> </b-tab>
            <b-tab title="Trends"> </b-tab>
            <b-tab title="Entries"> </b-tab>
            <b-tab title="Questions"> </b-tab>
        </b-tabs>


        <b-tabs v-if="renderComponent" lazy nav-class="invisible" v-model="activeTab">
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
                renderComponent: true,
                activeTab: 0,
                pollId: 0,
                tmpQID: 0,
                timer: '',
                stats: []
            }
        },
        watch: {
            stats: function () {
                this.forceRerender()
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
                loadEntries: 'loadEntries',
                loadPollAnswers: 'loadPollAnswers'
            }),
            equalStats(a, b) {
                const a1 = JSON.stringify(a)
                console.log(a1)
                const b1 = JSON.stringify(b)
                if (a1.length === b1.length) return true;
            },
            async fetchEventList() {
                await this.loadStatistics(this.poll.id)
                await this.loadEntries(this.poll.id)
                if (!this.equalStats(this.stats, this.statistics)) {
                    this.stats = this.statistics
                }
            },
            doSwitch(qId) {
                this.tmpQID = qId;
                this.activeTab = 4
            },
            forceRerender() {
                this.renderComponent = false;
                this.$nextTick(() => {
                    this.renderComponent = true;
                });
            },
            cancelAutoUpdate () {
                clearInterval(this.timer)
            }
        },
        created() {
            this.fetchEventList()
            const timeout = 300000; // update statistics every 5 minutes
            this.timer = setInterval(this.fetchEventList, timeout)
        },
        async mounted() {
            this.loadPollAnswers(this.poll.id);
            this.loadStatistics(this.poll.id);
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
        //position: absolute;
        //margin-top: -8px;
        top: 122px;
        background-color: $floating-background-color;
    }

</style>
