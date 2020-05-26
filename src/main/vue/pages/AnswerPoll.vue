<template>
    <div class="my-back">
        <b-row align-h="center">
            <h1>Fill your our polls</h1>
        </b-row>
        <b-container class="my-container">
            <b-row style="text-align: center" class="my-row">
                <b-col >
                    <AnswerPollTableList v-bind:polls="polls"/>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>


<script>

    import AnswerPollTableList from "../components/AnswerPollTableList";
    import {mapState, mapActions} from "vuex";

    export default {
        name: "AnswerPoll",
        created() {
            this.requestPolls()
        },
        computed: mapState({
            polls: state => state.polls
        }),
        methods: {
            ...mapActions(['requestPolls'])
        },
        components: {
            AnswerPollTableList
        },
    }
</script>

<style scoped>
    .my-back {
        min-height: 100vh;
        background-color: lightgray
    }
</style>
