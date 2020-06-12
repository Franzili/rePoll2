<template>
    <b-container fluid="lg">

        <!-- To make the tab bar fit on the right side of the title,
             we actually use b-tab Components: one for the navigation,
             and one to actually show the contents. they are coupled
             using v-bind. -->
        <b-row class="primary-tab-bar sticky align-items-baseline">
            <b-col>
                <h3>Titel: {{ poll.title }}</h3>
            </b-col>
            <b-col>
                <b-nav pills align="right">

                    <b-nav-item :active="$route.name === 'config'"
                        to="/poll-tabbed/"
                        title="Configure"> Configure
                    </b-nav-item>

                    <b-nav-item :active="$route.name === 'edit'"
                        to="/poll-tabbed/edit"
                        :disabled="poll.status !== 'IN_PROCESS'" title="Edit"> Edit
                    </b-nav-item>

                    <b-nav-item :active="$route.name === 'statistics'"
                        to="/poll-tabbed/statistics"
                        :disabled="poll.status === 'IN_PROCESS'" title="Statistics"> Statistics
                    </b-nav-item>

                </b-nav>
            </b-col>
        </b-row>

        <b-row>
            <b-col>
                <router-view></router-view>
            </b-col>
        </b-row>
        <!--
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
        -->
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

    //import ConfigurePoll from "../components/poll-tabs/configure/ConfigurePoll";
    //import CreatePoll from "../components/poll-tabs/edit/EditPoll";
    //import PollStats from "../components/poll-tabs/stats/PollStats";
    import {mapState} from "vuex";
    export default {
        name: "PollTabbed",
        data() {
            return {
                activeTab: 0,
                TAB_CONFIGURE: 0,
                TAB_EDIT: 1,
                TAB_STATISTICS: 2,
                bla: true
            }
        },
        computed: {
            ...mapState('currentPoll', {
                poll: 'poll'
            })
        },
        /*methods: {
            ...mapActions('currentPoll', {
                loadPoll: 'load',
            })
        },
        async mounted() {
            let pollId = this.$route.params.pollId
            await this.loadPoll(pollId)
        },*/
        //components: {ConfigurePoll, CreatePoll, PollStats}
    }
</script>

<style lang="scss" scoped>
    @import "../assets/stylesheet.scss";

    .primary-tab-bar {
        top: 80px;
        background-color: $floating-background-color;
    }

</style>
