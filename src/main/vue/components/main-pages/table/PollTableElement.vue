<template>
    <b-card
        border-variant="primary"
        :title="poll.title"
        align="left"
        bg-variant="light"
    >
        <b-row class="align-items-center">

            <b-col align-self="start">
                <p class="status">{{this.pollStatus}}</p>
            </b-col>

            <b-col
                align-self="center">
                <p v-show="poll.status !== 'IN_PROCESS'&& poll.status !== 'READY'"
                ><span class="participants">Participants: </span>{{poll.pollEntries}}</p>
            </b-col>

            <b-col cols="4" style="text-align: end">
                <span @click="loadTo" class="configLink"
                data-toggle="tooltip"
                title="Go to the Configuration-Page">Setup
                </span>
            </b-col>
            <b-col cols="2">
                <b-button variant="primary"
                        @click="copyPoll">
                    Copy Poll
                </b-button>
            </b-col>
        </b-row>

    </b-card>
</template>

<script>
    import {mapActions} from "vuex";

    export default {
        name: "PollListElement",
        props: ["poll"],
        data() {
            return {
                pollStatus: ''
            }
        },
        beforeMount() {
            switch (this.poll.status) {
                case 'IN_PROCESS':
                    this.pollStatus = 'in process';
                    break;
                case 'READY':
                    this.pollStatus = 'ready';
                    break;
                case 'ACTIVATED':
                    this.pollStatus = 'activated';
                    break;
                case 'DEACTIVATED':
                    this.pollStatus = 'deactivated';
                    break;
                case null:
                    this.pollStatus = ''
            }
        },
        methods: {
            ...mapActions('currentPoll', {
                loadPoll: "load"
            }),
            ...mapActions('myPolls', {
                duplicatePoll: "duplicate"
            }),
            async loadTo() {
                await this.loadPoll(this.poll.id)
                return this.$router.push({
                    name: 'poll-tabbed',
                    params: {
                        pollId: this.poll.id
                    }
                })
            },
            async copyPoll() {
                let newPoll = await this.duplicatePoll(this.poll.id);
                return this.$router.push({
                    name: 'edit-poll',
                    params: {
                        pollId: newPoll.id
                    }
                })
            }
        },

    }
</script>

<style scoped>
    .configLink {
        font-size: 18px;
        color: #7F7E7F;
        cursor:pointer;
    }
    .configLink:hover {
        text-decoration: underline
    }
    .status {
        font-size: 18px;
        font-style: italic;
        color: #3eab37;
    }
    .participants {
        font-style: italic;
        color: #3eab37;
    }
</style>
