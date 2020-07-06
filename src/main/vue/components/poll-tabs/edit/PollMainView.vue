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
                  v-on:editFinished="onItemEditFinished($event)"
                  v-on:remove="onRemove($event)"/>
    </draggable>
</template>

<script>
    import {mapActions, mapState} from "vuex"

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
            },
            ...mapState('consistencies', {
                consistencies: 'currentConsist'
            })
        },
        methods: {
            ...mapActions('currentPoll', {
                updatePollItem: 'updatePollItem',
                removePollItem: 'removePollItem'
            }),
            ...mapActions('consistencies', {
                dltConst: 'delete'
            }),
            onItemEditStarted(item) {
                if (this.currentlyEditing) {
                    this.currentlyEditing.setEditing(false);
                }
                this.currentlyEditing = item
            },
            /* Todo: Make sure that all choices are send together when updating ChoiceQuestions.
            Todo: This is necessary for correct integration of the backend
            */
            onItemEditFinished(item) {
                if (this.currentlyEditing === item) {
                    this.currentlyEditing = null
                    this.updatePollItem(item.model);
                    this.$root.$emit('update-questions')
                }
            },
            onRemove(item) {
                let dltCmds = []
                for (let i = 0; i < this.consistencies.length; i++) {
                    let currentConsist = this.consistencies[i]
                    for (let j = 0; j < currentConsist.questions.length; j++) {
                        if (currentConsist.questions[j].id === item.id) {
                            dltCmds.push({
                                pollId: this.$route.params.pollId,
                                constId: this.consistencies[i].id
                            })
                            break;
                        }
                    }
                }
                if (dltCmds.length > 0) {
                    this.$bvModal.msgBoxConfirm(
                        'Corresponding consistency groups will be delete as well. Do you wish to proceed?',
                        {
                        title: ' ',
                        headerBgVariant: 'info',
                        centered: true,
                        okVariant: 'secondary',
                        cancelVariant: 'primary',
                        headerBorderVariant: 'dark',
                        footerBorderVariant: 'dark',
                        hideHeaderClose: true,
                        size: 'sm',
                    }).then(value => {
                        if (value) {
                            this.removeQuestionAndConsistency(dltCmds, item)
                        }
                    })
                } else {
                    this.removePollItem(item.id);
                }
            },
            async removeQuestionAndConsistency(dltCmds, item) {
                if (dltCmds.length > 0) {
                    for (let k = 0; k < dltCmds.length; k++) {
                        await this.dltConst(dltCmds[k])
                    }
                    this.removePollItem(item.id);
                } else {
                    this.removePollItem(item.id);
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
