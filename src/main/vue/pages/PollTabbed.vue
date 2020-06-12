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

    </b-container>
</template>

<script>

    import {mapState} from "vuex";
    export default {
        name: "PollTabbed",
        computed: {
            ...mapState('currentPoll', {
                poll: 'poll'
            })
        },
    }
</script>

<style lang="scss" scoped>
    @import "../assets/stylesheet.scss";

    .primary-tab-bar {
        top: 80px;
        background-color: $floating-background-color;
    }

</style>
