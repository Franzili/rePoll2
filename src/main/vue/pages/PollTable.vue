<template>
    <b-container>
        <!-- make both collapsalbe items, with polls to Fill out expandet -->

        <!-- users Polls  to fill out
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
        -->

        <!-- users own Polls -->
        <b-container v-if="loaded"
            class="my-container">
            <b-row align-h="center">
                <h2>Poll Table</h2>
            </b-row>
            <b-row>
                <b-col >
                    <PollTableList v-bind:polls="polls"/>
                </b-col>
            </b-row>
        </b-container>

    </b-container>
</template>

<script>

    import PollTableList from "../components/main-pages/table/PollTableList";
    //import AnswerPollTableList from "../components/main-pages/table/AnswerPollTableList";
    import {mapState, mapActions} from "vuex";

    export default {
        name: "PollTable",
        data() {
            return {
                loaded: false
            }
        },
        async mounted() {
            this.loaded = false
            await this.loadPolls();
            this.loaded = true
            // this.loadAssigned();
        },

        computed: {
            ...mapState('myPolls', {
                polls: 'polls',
                //assigned: 'assigned'
            })
        },
        methods: {
            ...mapActions('myPolls', {
                loadPolls: 'load',
                //loadAssigned: 'loadAssigned',
            })
        },
        components: {
            PollTableList,
            //AnswerPollTableList
        },

    }
</script>

<style scoped>
</style>
