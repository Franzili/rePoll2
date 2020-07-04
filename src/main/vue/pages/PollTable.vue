<template>
    <b-container>
        <b-container v-if="loaded"
            class="my-container">
            <b-row align-h="center">
                <h2>Overview</h2>
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
            await this.loadPolls()
            this.loaded = true
        },

        computed: {
            ...mapState('myPolls', {
                polls: 'polls',
            })
        },
        methods: {
            ...mapActions('myPolls', {
                loadPolls: 'load',
            }),
        },
        components: {
            PollTableList,
        },

    }
</script>

<style scoped>
</style>
