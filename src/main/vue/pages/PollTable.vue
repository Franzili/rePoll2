<template>
    <div class="my-back">
        <nav-bar></nav-bar>
        <HelloWorld style="text-align:center;" class="ml-auto" msg="Meine Umfragen"/>
        <b-container class="my-container">
            <b-button class="my-button" @click="addNewPoll">+</b-button>
            <b-row style="text-align: center" class="my-row">
                <b-col >
                    <PollTableList v-bind:polls="polls"/>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>

    import HelloWorld from '../components/HelloWorld.vue'
    import NavBar from "../components/NavBar";
    import PollTableList from "../components/PollTableList";
    import {mapState, mapActions} from "vuex";
    import axios from "axios";

    export default {
        name: "PollTable",
        computed: {
            ...mapState({
                polls: state => state.polls
            })
        },
        methods: {
            addNewPoll() {
                let pollCmd = {title: "new Poll"};
                axios.post('/api/v1/polls/', pollCmd)
                    .then((response) => {
                        console.log(response.data);
                        return this.$router.push({name:'create', params: {tmpPollID: response.data.id}})
                    });
            },
            ...mapActions([
                'requestPolls'
            ])
        },
        created: function () {
            this.requestPolls()
        },
        components: {
            NavBar,
            HelloWorld,
            PollTableList
        },
    }
</script>

<style scoped>
    .my-button {
        background-color: black;
        margin-left: 15px;
        font-size: 140%;
        width: 50px;
        height: 50px;
    }

    .my-back {
        min-height: 100vh;
        background-color: lightgray
    }
</style>
