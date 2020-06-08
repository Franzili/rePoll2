<template>
    <!--
        Renders a poll.
    -->
    <ul class="poll-main-view">
        <PollItem v-for="item in pollStructure"
                  v-bind:key="item.id"
                  :model="item"
                  :id="'pollItem-' + item.id"
                  v-on:editStarted="onItemEditStarted($event)"
                  v-on:editFinished="onItemEditFinished($event)"/>
    </ul>
</template>

<script>
    import {mapGetters, mapActions} from "vuex"

    import PollItem from "./poll-items/PollItem";

    export default {
        name: "PollMainView",
        data() {
            return {
                currentlyEditing: null
            }
        },
        computed: {
            ...mapGetters('currentPoll', {
                pollStructure: 'pollStructureFlat'
            })
        },
        methods: {
            ...mapActions('currentPoll', {
                updatePollItem: 'updatePollItem'
            }),
            onItemEditStarted(item) {
                if (this.currentlyEditing) {
                    this.currentlyEditing.setEditing(false);
                }
                this.currentlyEditing = item
            },
            onItemEditFinished(item) {
                if (this.currentlyEditing === item) {
                    this.currentlyEditing = null
                    this.updatePollItem(item.model);
                }
            }
        },
        components: { PollItem }
    }
</script>

<style lang="scss" scoped>
    .poll-main-view {
        // turn off default list styling
        list-style-type: none;
        padding-left: 0;
    }
</style>
