<template>
    <b-container fluid="lg">

        <!-- To make the tab bar fit on the right side of the title,
             we actually use b-tab Components: one for the navigation,
             and one to actually show the contents. they are coupled
             using v-bind. -->

        <b-row class="primary-tab-bar sticky align-items-baseline">
            <b-col>
                <b-row>


                <b-col cols="9">
                    <div  style="float: left">
                        <h3 class="mr-2">Titel:</h3>
                    </div>

                    <div  style="float: left">
                        <EditableLabel
                            tag="h3"
                            :value="pollTitle"
                            :editing="editTitle"
                            v-on:valueChanged="pollTitle = $event">
                            <!-- is this applied when submitting poll? -->
                        </EditableLabel>
                    </div>
                </b-col>
                <b-col>
                    <div class="ml-5" style="float: left">
                        <b-button variant="outline-secondary" v-if="!editTitle"
                                  @click="setEditingTitle(true)">
                            <b-icon-pencil/>
                        </b-button>
                        <b-button variant="outline-secondary" v-else
                                  @click="setEditingTitle(false)">
                            <b-icon-check/>
                        </b-button>
                    </div>
                </b-col>
                </b-row>
            </b-col>
            <b-col>
                <b-tabs lazy pills align="right" v-model="activeTab">

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
    import CreatePoll from "../components/poll-tabs/edit/EditPoll";
    import PollStats from "../components/poll-tabs/stats/PollStats";
    import {mapState, mapActions} from "vuex";
    import EditableLabel from "../components/EditableLabel";
    export default {
        name: "PollTabbed",
        data() {
            return {
                activeTab: 0,
                TAB_CONFIGURE: 0,
                TAB_EDIT: 1,
                TAB_STATISTICS: 2,

                pollTitle: '',
                editTitle: false
            }
        },
        computed: {
            ...mapState('currentPoll', {
                poll: 'poll'
            })
        },
        methods: {
            ...mapActions('currentPoll', {
                loadPoll: 'load',
            }),
            setEditingTitle(edit) {
                this.editTitle = edit;
            }
        },
        created() {
            let pollId = this.$route.params.pollId;
            this.loadPoll(pollId)
        },
        components: {EditableLabel, ConfigurePoll, CreatePoll, PollStats}
    }
</script>

<style lang="scss" scoped>
    @import "../assets/stylesheet.scss";

    .primary-tab-bar {
        top: 80px;
        background-color: $floating-background-color;
    }

</style>
