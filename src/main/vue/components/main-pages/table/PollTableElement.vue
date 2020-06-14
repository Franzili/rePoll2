<template>
    <b-card
        border-variant="primary"
        :title="poll.title"
        align="left"
        bg-variant="light"
    >
        <b-row>

            <b-col align-self="start">
                <p class="status">{{this.pollStatus}}</p>
            </b-col>

            <b-col
                align-self="center">
                <p v-show="poll.status !== 'IN_PROCESS'"
                ><span class="participants">Participants: </span>{{poll.pollEntries}}</p>
            </b-col>

            <b-col align-self="end" style="text-align: center">
                <p>
                    <router-link class="configLink"
                                 :to="{ name: 'poll-tabbed', params: {pollId: poll.id}}"
                    >Setup
                    </router-link>
                </p>
            </b-col>
        </b-row>

    </b-card>
</template>

<script>
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
        }
    }
</script>

<style scoped>
    .configLink {
        font-size: 18px;
        color: #7F7E7F;
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
