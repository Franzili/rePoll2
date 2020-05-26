<template>
    <b-container>

        <b-row align-h="center">
            <h1>My Polls</h1>
        </b-row>

        <b-row>
            <b-col col>
                <PollTableList v-bind:polls="polls"/>
            </b-col>
        </b-row>


    </b-container>
</template>

<script>

    import PollTableList from "../components/main-pages/table/PollTableList";
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
            PollTableList
        },
    }
</script>

<style scoped>
</style>
