<template>
    <b-container fluid="lg" v-if="loaded">
        <b-row v-if="loaded" class="primary-tab-bar relative align-items-baseline">
            <b-col class="poll-title">

                        <h3 v-if="$route.name === 'edit'" class="mr-2">Titel:</h3>

                        <EditableLabel
                            tag="h3"
                            :value="poll.title"
                            :editing="editTitle"
                            v-on:valueChanged="pollTitle = $event">
                        </EditableLabel>

                    <div v-if="$route.name === 'edit-poll'" class="ml-3">
                        <b-button-group >
                            <b-button size="sm" variant="outline-secondary" v-if="!editTitle"
                                      @click="setEditingTitle(true)">
                                <b-icon-pencil/>
                            </b-button>
                            <b-button variant="outline-secondary" v-else
                                      @click="setEditingTitle(false)">
                                <b-icon-check scale="1.2"/>
                            </b-button>
                        </b-button-group>
                    </div>
            </b-col>


            <b-col>
                <b-nav align="right">
                    <b-button variant="primary"
                        active-class="active"
                    :to="{ name: 'configure-poll', params: { pollId: $route.params.pollId }}">Configure

                    </b-button>
                    <b-button style="margin-left: 0.25rem"
                        variant="primary"
                        active-class="active"
                                :to="{ name: 'edit-poll', params: { pollId: $route.params.pollId }}"
                                :disabled="poll.status === 'LAUNCHED'">
                        Edit
                    </b-button>
                    <b-button style="margin-left: 0.25rem"
                        variant="primary"
                        active-class="active"
                                :to="{ name: 'poll-stats', params: { pollId: $route.params.pollId }}"
                                :disabled="poll.status === 'EDITING'">
                        Statistics
                    </b-button>
                </b-nav>
            </b-col>
        </b-row>

        <b-row>
            <b-col>
                <router-view/>
            </b-col>
        </b-row>

    </b-container>
</template>

<script>
    import {mapActions, mapState} from "vuex";
    import EditableLabel from "../components/EditableLabel";
    export default {
        name: "PollTabbed",
        data() {
            return {
                loaded: false,

                pollTitle: '',
                editTitle: false
            }
        },
        computed: {
            ...mapState('currentPoll', {
                poll: 'poll',
                iteration: 'iteration'
            })
        },
        methods: {
            ...mapActions('currentPoll', {
                loadPoll: 'load',
                update: 'update'
            }),
            setEditingTitle(edit) {
                this.editTitle = edit;
                if (!edit) {
                    if (this.pollTitle === "") {
                        this.pollTitle = "Unnamed Poll"
                    }
                    this.update({id: this.poll.id, title: this.pollTitle});
                }
            }
        },
        async mounted() {
            this.loaded = false;
            let pollId = this.$route.params.pollId;
            await this.loadPoll(pollId);
            this.pollTitle = this.poll.title;
            this.loaded = true
        },
        watch: {
            activeTab: function () {
                this.editTitle = false;
            }
        },
        components: {
            EditableLabel
        },
    }
</script>

<style lang="scss" scoped>
    @import "../assets/stylesheet.scss";

    .primary-tab-bar {
        top: 80px;
        //top: 0;
        //position: relative;
        background-color: $floating-background-color;
    }

    .poll-title {
        display: flex;
        align-items: center;
    }

    .nav-link.disabled {
        font-style: italic;
    }
</style>
