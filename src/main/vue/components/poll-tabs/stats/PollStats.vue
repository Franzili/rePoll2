<template>
    <div>
        <b-tabs v-if="renderComponent" small lazy class="relative stats-tab-bar" v-model="activeTab">
            <b-tab title="Overview"> </b-tab>
            <b-tab title="Compare"> </b-tab>
            <b-tab title="Trends"> </b-tab>
            <b-tab title="Entries"> </b-tab>
            <b-tab title="Questions"> </b-tab>
        </b-tabs>


        <b-tabs v-if="renderComponent" lazy nav-class="invisible" v-model="activeTab">
            <b-tab><Overview
                v-bind:iteration-list="iterationDateList"
            ></Overview></b-tab>
            <b-tab><Compare v-bind:iteration-list="iterationDateList"
            ></Compare></b-tab>
            <b-tab><Trends></Trends></b-tab>
            <b-tab><Entries v-bind:iteration-list="iterationDateList"
            ></Entries></b-tab>
            <b-tab><Questions v-bind:iteration-list="iterationDateList"
            ></Questions></b-tab>
        </b-tabs>
    </div>
</template>

<script>
    import Overview from "./Overview";
    import Compare from "./Compare";
    import Trends from "./Trends";
    import Entries from "./Entries";
    import Questions from "./Questions";
    import {mapActions, mapGetters, mapState} from "vuex";

    export default {
        name: "PollStats",
        data() {
            return {
                iterationDateList: [],
                renderComponent: true,
                activeTab: 0,
                pollId: 0,
                tmpQID: 0,
                timer: '',
                localStats: [],
                dateTimeFormat: new Intl.DateTimeFormat('en', {
                    year: 'numeric',
                    month: 'short',
                    day: '2-digit',
                    weekday: 'short',
                    hour: 'numeric',
                    minute: 'numeric',
                }),
            }
        },
        watch: {
            localStats: function () {
                this.forceRerender()
            }
        },
        computed: {
            ...mapState('currentPoll', {
                poll: 'poll',
                statistics: 'statistics',
                iteration: 'iteration'
            }),
            ...mapGetters('currentPoll', {
                getIterationList: 'getIterationsListFormed'
            })
        },
        methods: {
            ...mapActions('currentPoll', {
                loadStatistics: 'loadPollStats',
                loadEntries: 'loadEntries',
                loadPollAnswers: 'loadPollAnswers'
            }),
            equalStats(a, b) {
                const a1 = JSON.stringify(a)
                const b1 = JSON.stringify(b)
                if (a1.length === b1.length) return true;
            },
            fetchEventList() {
                this.loadStatistics(this.poll.id)
                //this.loadEntries(pollId: this.poll.id)
                if (!this.equalStats(this.localStats, this.statistics)) {
                    this.localStats = this.statistics
                }
            },
            forceRerender() {
                this.renderComponent = false;
                this.$nextTick(() => {
                    this.renderComponent = true;
                });
            },
            cancelAutoUpdate () {
                clearInterval(this.timer)
            },
        },
        created() {
            this.fetchEventList()
            const timeout = 300000; // update statistics every 5 minutes
            this.timer = setInterval(this.fetchEventList, timeout)
        },
        mounted() {
            this.iterationDateList = this.getIterationList
            this.iterationDateList.forEach(iteration => {
                iteration.text = new Intl.DateTimeFormat('en', {
                    year: 'numeric',
                    month: 'short',
                    day: '2-digit',
                    weekday: 'short',
                    hour: 'numeric',
                    minute: 'numeric',
                }).format(new Date(iteration.text))
            })
            this.$store.commit('currentPoll/setIterationId',this.iterationDateList[0].value)
            //this.loadPollAnswers(this.poll.id); // not needed in this version dunno
            //this.loadStatistics(this.poll.id); // life stats fucked this up xD
            //await this.loadEntries(this.poll.id)
        },
        beforeDestroy() {
            this.$store.commit('currentPoll/setIterationId', 0)
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
