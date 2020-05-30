<template>
    <b-container>
        <!-- make both collapsalbe items, with polls to Fill out expandet -->

        <!-- users Polls  to fill out-->
        <b-container class="my-container">
            <b-row align-h="center">
                <h2>Your polls to fill out</h2>
            </b-row>
            <b-row style="text-align: center" class="my-row">
                <b-col >
                    <AnswerPollTableList v-bind:polls="polls"/>
                </b-col>
            </b-row>
        </b-container>

        <!-- users own Polls -->
        <b-container class="my-container">
            <b-row align-h="center">
                <h2>Your own Polls</h2>
            </b-row>
            <b-row style="text-align: center" class="my-row">
                <b-col >
                    <PollTableList v-bind:polls="polls"/>
                </b-col>
            </b-row>
        </b-container>

    </b-container>
</template>

<script>

    import PollTableList from "../components/main-pages/table/PollTableList";
    import AnswerPollTableList from "../components/main-pages/table/AnswerPollTableList";
    import {mapState, mapActions} from "vuex";

    export default {
        name: "PollTable",
        created: function () {
            this.requestPolls()
        },
        computed: {
            ...mapState({
                polls: state => state.polls
            })
        },
        methods: {
            ...mapActions(['requestPolls'])
        },
        components: {
            PollTableList,
            AnswerPollTableList
        },
    }
</script>

<style scoped>
</style>
