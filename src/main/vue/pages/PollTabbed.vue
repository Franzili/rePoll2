<template>
    <b-container>

        <!-- To make the tab bar fit on the right side of the title,
             we actually use b-tab Components: one for the navigation,
             and one to actually show the contents. they are coupled
             using v-bind. -->

        <b-row class="primary-tab-bar sticky align-items-baseline">
            <b-col>
                <h3>Titel: Moby Dick</h3>
            </b-col>
            <b-col>
                <b-tabs pills align="right" v-model="activeTab">

                    <b-tab active title="Configure">
                    </b-tab>

                    <b-tab title="Edit">
                    </b-tab>

                    <b-tab title="Statistics">
                    </b-tab>

                </b-tabs>
            </b-col>
        </b-row>

        <b-row>
            <b-col>
                <b-tabs lazy nav-class="invisible" v-model="activeTab">
                    <b-tab>
                        <ConfigurePoll></ConfigurePoll>
                    </b-tab>
                    <b-tab>
                        <CreatePoll></CreatePoll>
                    </b-tab>
                    <b-tab>
                        <PollStats></PollStats>
                    </b-tab>

                </b-tabs>
            </b-col>
        </b-row>


    </b-container>
</template>

<script>
    /*
    how backend polls are provided here:
    poll() <- poll object
    easy access:
    poll.title (in js with this. ..)
    poll.id    (in js with this. ..)
    where can i see poll objects?
    in your browser with vue plugin, an empty poll could look like this:
    (in your vue dev plugin)
    computed
        poll:
            creationTime:
            creator:
                email:
                fullName:
                id:
                ownPolls: []
                username:
            entries: []
            id:
            lastEditTime:
            lastEditor:
            owner:
            questions: [
                0:
                    charLimit:
                    id:
                    questionOrder:
                    title:
                    type:
            ]
            sections: []
            status:
            title:
    */

    import ConfigurePoll from "../components/poll-tabs/configure/ConfigurePoll";
    import CreatePoll from "./CreatePoll";
    import PollStats from "../components/poll-tabs/stats/PollStats";
    import {mapActions, mapGetters} from "vuex";
    export default {
        name: "PollTabbed",
        data() {
            return {
                tmpID: 0,
                activeTab: '',
                TAB_CONFIGURE: 0,
                TAB_EDIT: 1,
                TAB_STATISTICS: 2,
            }
        },
        created() {
            this.tmpID = this.$route.params.tmpPollID
            //this.activeTab = this.$route.params.tabWish
            this.requestPoll(this.tmpID)

        },
        computed: {
            ...mapGetters(['getPoll']),
            poll() {
                return this.getPoll(this.tmpID)
            }
        },
        methods: {
            ...mapActions(['requestPoll'])

        },
        components: {ConfigurePoll, CreatePoll, PollStats}
    }
</script>

<style lang="scss" scoped>
    @import "../assets/stylesheet.scss";

    .primary-tab-bar {
        top: 65px;
        background-color: $floating-background-color;
    }

</style>
