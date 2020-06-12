<template>
    <b-card
        border-variant="primary"
        :title="poll.title"
        align="left"
        bg-variant="light"
    >
        <b-row>

            <b-col cols="8">
                <p class="status">{{poll.status}}</p>
            </b-col>

            <b-col cols="4" style="text-align: center">
                <span @click="loadTo" class="configLink">Setup
                </span>

            </b-col>
        </b-row>

    </b-card>
</template>

<script>
    import {mapActions} from "vuex";

    export default {
        name: "PollListElement",
        props: ["poll"],
        methods: {
            ...mapActions('currentPoll', {
                loadPoll: "load"
            }),
            async loadTo() {
                await this.loadPoll(this.poll.id)
                return this.$router.push('/poll-tabbed/')
            }
        }
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
        font-size: 14px;
        font-style: italic;
    }
</style>
