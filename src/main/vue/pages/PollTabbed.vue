<template>
    <b-container fluid="lg">
        <b-row class="primary-tab-bar sticky align-items-baseline">
            <b-col>
                <h3>Titel: {{ poll.title }}</h3>
            </b-col>
            <b-col>
                <b-nav pills align="right">

                    <b-nav-item active-class="active"
                                :to="{ name: 'edit-poll', params: { pollId: $route.params.pollId }}">
                        Edit
                    </b-nav-item>
                    <b-nav-item active-class="active"
                                :to="{ name: 'configure-poll', params: { pollId: $route.params.pollId }}">
                        Configure
                    </b-nav-item>
                    <b-nav-item active-class="active"
                                :to="{ name: 'poll-stats', params: { pollId: $route.params.pollId }}">
                        Stats
                    </b-nav-item>

                </b-nav>
            </b-col>
        </b-row>

        <b-row>
            <b-col>
                <router-view/>
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

    import {mapState, mapActions} from "vuex";
    export default {
        name: "PollTabbed",
        computed: {
            ...mapState('currentPoll', {
                poll: 'poll'
            })
        },
        methods: {
            ...mapActions('currentPoll', {
                loadPoll: 'load',
            })
        },
        created() {
            let pollId = this.$route.params.pollId
            this.loadPoll(pollId)
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
