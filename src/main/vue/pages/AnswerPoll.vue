<template>
    <div class="my-back">
        <HelloWorld style="text-align:center;" class="ml-auto" msg="Fill yout our polls"/>
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

    import HelloWorld from '../components/HelloWorld.vue'
    import AnswerPollTableList from "../components/AnswerPollTableList";
    import {mapState, mapActions} from "vuex";

    export default {
        name: "AnswerPoll",
        computed: mapState({
            polls: state => state.polls
        }),
        methods: {
            ...mapActions([
                'requestPolls'
            ])
        },
        created() {
            this.requestPolls()
        },
        components: {
            HelloWorld,
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
