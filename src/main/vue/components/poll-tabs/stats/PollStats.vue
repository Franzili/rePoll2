<template>
    <div>
        <b-tabs small lazy class="sticky stats-tab-bar" v-model="activeTab">
            <b-tab v-if="renderComponent" title="Overview"> </b-tab>
            <b-tab title="Compare"> </b-tab>
            <b-tab title="Trends"> </b-tab>
            <b-tab title="Entries"> </b-tab>
            <b-tab title="Questions"> </b-tab>
        </b-tabs>


        <b-tabs lazy nav-class="invisible" v-model="activeTab">
            <b-tab><Overview v-if="renderComponent" v-on:toQuestion="doSwitch($event)"></Overview></b-tab>
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
            stats: function (oldVale, newVal) {
                this.forceRerender()
                console.log(oldVale + newVal)
                console.log("alles kagge")
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
            equalStats(a, b) {
                if (a === b) return true;
                if (a == null || b == null) return false;
                if (a.length != b.length) return false;
                for (var i = 0; i < a.length; ++i) {
                    if (!Object.is(a[i], b[i])) return false;
                }
                return true;
            },
            fetchEventList() {
                this.loadStatistics(this.poll.id)
                if (!this.equalStats(this.stats, this.statistics)) {
                    console.log("not equal")
                    this.stats = this.statistics
                }
                console.log("funzt")
            },
            cancelAutoUpdate () {
                clearInterval(this.timer)
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
            }
        },
        created() {
            this.fetchEventList()
            this.timer = setInterval(this.fetchEventList, 3000)
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
