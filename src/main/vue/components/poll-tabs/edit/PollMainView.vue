<template>
    <!--
        Renders a poll.
    -->
    <draggable tag="ul"
               class="poll-main-view"
               v-model="pollStructure"
               :group="{ name: 'pollEditor' }"
               handle=".handle">
        <PollItem v-for="item in pollStructure"
                  v-bind:key="item.id"
                  :model="item"
                  :id="'pollItem-' + item.id"
                  v-on:editStarted="onItemEditStarted($event)"
                  v-on:editFinished="onItemEditFinished($event)"/>
    </draggable>
</template>

<script>
    import {mapActions} from "vuex"

    import PollItem from "./poll-items/PollItem";

    import draggable from "vuedraggable";

    export default {
        name: "PollMainView",
        data() {
            return {
                currentlyEditing: null,
            }
        },
        computed: {
            pollStructure: {
                get() {
                    return this.$store.getters["currentPoll/pollStructureFlat"];
                },
                set(value) {
                    this.$store.dispatch('currentPoll/updateStructure', value);
                }
            }
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
        components: { PollItem, draggable }
    }
</script>

<style lang="scss" scoped>
    .poll-main-view {
        // turn off default list styling
        list-style-type: none;
        padding-left: 0;
    }
</style>
