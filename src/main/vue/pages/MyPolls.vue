<template>
    <div class="my-back">
        <nav-bar></nav-bar>
        <HelloWorld style="text-align:center;" class="ml-auto" msg="Overview over your polls"/>
        <!-- users own Polls -->
        <b-container class="my-container">
            <b-row style="text-align: center" class="my-row">
                <b-col >
                    <PollTableList v-bind:polls="polls"/>
                </b-col>
            </b-row>
        </b-container>
        <!-- users Polls  to fill out-->
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
    import NavBar from "../components/NavBar";
    import AnswerPollTableList from "../components/main-pages/table/AnswerPollTableList";
    import PollTableList from "../components/PollTableList";
    import {mapState, mapActions} from "vuex";

    export default {
        name: "MyPolls",
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
            NavBar,
            HelloWorld,
            AnswerPollTableList,
            PollTableList
        },
    }
</script>

<style scoped>

    .my-back {
        min-height: 100vh;
        background-color: lightgray
    }
</style>
