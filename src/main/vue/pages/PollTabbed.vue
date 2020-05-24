<template>
    <b-container>

        <!-- To make the tab bar fit on the right side of the title,
             we actually use b-tab Components: one for the navigation,
             and one to actually show the contents. they are coupled
             using v-bind. -->

        <b-row class="sticky align-items-baseline" id="primary-tab-bar">
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
                        Stat me!
                    </b-tab>

                </b-tabs>
            </b-col>
        </b-row>


    </b-container>
</template>

<script>
    import ConfigurePoll from "../components/poll-tabs/configure/ConfigurePoll";
    import CreatePoll from "./CreatePoll";
    export default {
        name: "PollTabbed",
        data() {
            return {
                activeTab: "Configure",
                TAB_CONFIGURE: 0,
                TAB_EDIT: 1,
                TAB_STATISTICS: 2
            }
        },
        components: {ConfigurePoll, CreatePoll}
    }
</script>

<style lang="scss" scoped>
    @import "../assets/stylesheet.scss";

    #primary-tab-bar {
        position: sticky;
        top:50;
    }

    .invisible {
        visibility: hidden;
    }

</style>
