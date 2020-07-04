<template>

    <b-container>
        <!--
        <p>
            {{polls}}
        </p>
        -->
        <b-row>
            <b-col>
                <p>
                    <b-button variant="primary"
                              class="addButton"
                              :disabled="!hasCreatorPrivileges"
                              v-on:click="addNewPoll">+</b-button>
                </p>
            </b-col>
            <b-col>
                <p>
                    sort by ...
                </p>
            </b-col>
            <b-col>
                <b-form-select v-model="selectedSort" :options="sortOptions"></b-form-select>
            </b-col>
            <b-col>
                <b-button @click="reverse">Reverse me</b-button>
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
                    {text: 'name', value: 'name'},
                    {text: 'creation date', value: 'createDate'},
                    {text: 'finish date', value: 'finishDate'},
                    {text: 'creator', value: 'creator'}
                ],
                selectedSort: null
            }
        },
        computed: {
            ...mapGetters('auth', {
                hasCreatorPrivileges: "hasCreatorPrivileges"
            })
        },
        methods: {
            ...mapActions('myPolls', {
                createPoll: "create"
            }),
            async addNewPoll() {
                let pollCmd = {
                    title: "Unnamed Poll"
                };
                let newPoll = await this.createPoll(pollCmd);
                return this.$router.push({
                    name: 'edit-poll',
                    params: {
                        pollId: newPoll.id
                    }
                })
            },
            reverse() {
                this.polls.reverse()
            }
        },
        watch: {
            selectedSort: function(val) {
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
                            console.log('hallo')
                            if (a.currentIteration === null && b.currentIteration === null) {
                                return 0
                            } else if (a.currentIteration === null) {
                                return 1
                            } else if (b.currentIteration === null) {
                                return -1
                            } else  {
                                return new Date(b.creationTime) - new Date(a.creationTime)
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
