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

                <router-link class="configLink"
                             :to="{ name: 'poll-tabbed', params: { pollId: poll.id } }"
                >Setup
                </router-link>

            </b-col>
        </b-row>

    </b-card>
</template>

<script>
    export default {
        name: "PollListElement",
        props: ["poll"]
    }
</script>

<style scoped>
    .configLink {
        font-size: 18px;
        color: #7F7E7F;
    }
    .status {
        font-size: 14px;
        font-style: italic;
    }
</style>
