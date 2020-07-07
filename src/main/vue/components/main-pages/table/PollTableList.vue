<template>

    <b-container>
        <b-row>
            <b-col>
                <p>
                    <b-button variant="primary"
                              class="addButton"
                              data-toggle="tooltip"
                              title="Create new Poll"
                              :disabled="!hasCreatorPrivileges"
                              @click="modalRadio = 'new'"
                              v-b-modal.new-poll
                              >+</b-button>
                    <b-modal
                        hide-header-close
                        centered
                        id="new-poll"
                        title="Create a new Poll"
                        @ok="prevent">
                        <b-form-radio-group v-model="modalRadio">
                            <b-row>
                                <b-col>
                                    <b-form-radio value="new">New Poll</b-form-radio>
                                    <b-form>
                                        <b-input :disabled="modalRadio !== 'new'"
                                            v-model="title"></b-input>
                                    </b-form>
                                </b-col>
                                <b-col>
                                    <b-form-radio value="copy">Copy Poll</b-form-radio>
                                    <b-form>
                                        <b-select v-model="copyOf"
                                            :disabled="modalRadio !== 'copy'"
                                            :options="pollOpt"></b-select>
                                    </b-form>
                                </b-col>
                            </b-row>

                        </b-form-radio-group>
                    </b-modal>
                </p>
            </b-col>
            <b-col>
                <b-button-toolbar class="float-right">
                    <h3>Sort by:</h3>
                    <b-form-group style="margin-left: 1rem">
                        <b-form-select v-model="selectedSort" :options="sortOptions"></b-form-select>
                    </b-form-group>
                    <b-form-group style="margin-left: 0.5rem">
                        <b-button variant="outline-secondary"
                                  @click="reverse">
                            <b-icon v-if="toUp" icon="chevron-up"></b-icon>
                            <b-icon v-else icon="chevron-down"></b-icon>
                        </b-button>
                    </b-form-group>
                </b-button-toolbar>
            </b-col>

        </b-row>

        <b-row v-bind:key="poll.id" v-for="poll in polls">
            <b-col>
                <p>
                    <PollTableElement v-bind:poll="poll"/>
                </p>
            </b-col>
        </b-row>
    </b-container>


</template>

<script>
    import PollTableElement from "./PollTableElement";
    import {mapActions, mapGetters} from "vuex";

    export default {
        name: "PollTableList",
        props: ["polls"],
        data() {
            return {
                sortOptions: [
                    {text: 'Creation Date', value: 'createDate'},
                    {text: 'Poll Name', value: 'name'},
                    {text: 'Finish Date', value: 'finishDate'},
                    {text: 'Creator', value: 'creator'}
                ],
                selectedSort: 'createDate',
                toUp: true,
                modalRadio: 'new',
                title: 'Unnamed Poll',
                pollOpt: [],
                copyOf: null,
            }
        },
        computed: {
            ...mapGetters('auth', {
                hasCreatorPrivileges: "hasCreatorPrivileges"
            })
        },
        created() {
            this.polls.forEach(poll => {
                this.pollOpt.push({value: poll.id, text: poll.title})
            })
        },
        methods: {
            ...mapActions('myPolls', {
                createPoll: "create",
                duplicatePoll: "duplicate"
            }),
            async addNewPoll(title) {
                let pollCmd = {
                    title: title
                };
                let newPoll = await this.createPoll(pollCmd);
                return this.$router.push({
                    name: 'edit-poll',
                    params: {
                        pollId: newPoll.id
                    }
                })
            },
            async copyPoll(pollId) {
                let newPoll = await this.duplicatePoll(pollId);
                return this.$router.push({
                    name: 'edit-poll',
                    params: {
                        pollId: newPoll.id
                    }
                })
            },
            reverse() {
                this.toUp = !(this.toUp === true)
                this.polls.reverse()
            },
            prevent(bvModalEvt) {
                bvModalEvt.preventDefault()
                this.handleSubmit()
            },
            handleSubmit() {
                if (this.modalRadio === 'new') {
                    this.addNewPoll(this.title)
                } else {
                    if (this.copyOf !== null) {
                        this.copyPoll(this.copyOf)
                    }
                }
            }
        },
        watch: {
            selectedSort: function(val) {
                this.toUp = true
                switch (val) {
                    case 'name':
                        this.polls.sort(function (a,b) {
                            return a.title.localeCompare(b.title)
                        })
                        break;
                    case 'createDate':
                        this.polls.sort(function (a,b) {
                            return new Date(b.creationTime) - new Date(a.creationTime)
                        })
                        break;
                    case 'finishDate':
                        this.polls.sort(function (a,b) {
                            if (a.status === 'EDITING' && b.status === 'EDITING') {
                                return 0
                            } else if (a.status === 'EDITING') {
                                return 1
                            } else if (b.status === 'EDITING') {
                                return -1
                            } else if (a.currentIteration === null && b.currentIteration === null) {
                                return 0
                            } else if (a.currentIteration === null) {
                                return 1
                            } else if (b.currentIteration === null) {
                                return -1
                            } else if (a.currentIteration.end === null && b.currentIteration.end === null) {
                                return 0
                            } else if (a.currentIteration.end === null) {
                                return 1
                            } else if (b.currentIteration.end === null) {
                                return -1
                            } else  {
                                return new Date(a.currentIteration.end) - new Date(b.currentIteration.end)
                            }
                        })
                        break;
                    case 'creator':
                        this.polls.sort(function (a,b) {
                            return a.title.localeCompare(b.title)
                        })
                        this.polls.sort(function (a,b) {
                            return a.creator.username.localeCompare(b.creator.username)
                        })
                        break;
                    case null:
                        break;
                }
            }
        },
        components: {
            PollTableElement
        }
    }
</script>

<style scoped>
    .addButton {
        font-size: 140%;
        width: 50px;
        height: 50px;
    }
</style>
