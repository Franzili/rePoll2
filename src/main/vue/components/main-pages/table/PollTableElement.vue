<template>
    <!-- :title="poll.title" -->
    <b-card
        no-body
        border-variant="primary"

        align="left"
        bg-variant="light"
    >

        <b-card-body>
            <b-row>
                <b-col>
                    <h4>
                {{poll.title}}
                    </h4>
                </b-col>
            </b-row>

            <b-row class="align-items-center">
                <!--
                <p>
                    {{poll}}
                </p>
                -->
                <!--
                <a @click="loadTo" style="cursor: pointer" class="stretched-link"></a>
                -->


                <b-col>
                    <h6>Status:</h6>
                    {{this.pollStatus}}
                </b-col>
                <b-col>
                    <h6>Creation Date:</h6>
                    {{poll.creationTime}}
                </b-col>
                <b-col>
                    <h6>Creator:</h6>
                    {{poll.creator.username}}
                </b-col>
                <b-col>
                    <h6>Participants:</h6>
                    0
                </b-col>
                <!--
                <b-col v-if="poll.currentIteration !== null">
                    {{poll.currentIteration.end}}
                </b-col>
                -->
                <!--
                <b-col
                    align-self="center">
                    <p v-show="poll.status !== 'EDITING'"
                    ><span class="participants">Participants: </span>{{poll.pollEntries}}</p>
                </b-col>

                <b-col cols="4" style="text-align: end">
                    <span @click="loadTo" class="configLink">Setup
                    </span>
                </b-col>
                <b-col cols="2">
                    <b-button variant="primary"
                              @click="copyPoll">
                        Copy Poll
                    </b-button>
                </b-col>
                -->
            </b-row>

            <b-row v-if="poll.pollIterations.length > 0">
                <!--
                <b-button v-b-toggle.iteration  variant="primary">Iterations</b-button>
                <b-collapse id="iteration" class="mt-2">
                    <b-table striped
                             hover
                             outlined
                             auto
                             :fields="fields"
                             :items="iterations"></b-table>
                </b-collapse>
                -->

                <!--
                <b-col>
                    <b-container v-bind:key="iteration.id" v-for="iteration in iterations">
                        <b-row>
                            <b-col>{{iteration.id}}</b-col>
                            <b-col>{{iteration.status}}</b-col>
                            <b-col>{{showDate(iteration.start)}}</b-col>
                            <b-col>{{showDate(iteration.end)}}</b-col>
                            <b-col>{{iteration.pollEntries.length}}</b-col>
                        </b-row>
                    </b-container>
                </b-col>
                -->
            </b-row>
        </b-card-body>



        <!--
        <b-card-body>


            <h4>
                {{poll.title}}
            </h4>

            <b-row class="align-items-center">

                <b-col align-self="start">
                    <p class="status">{{this.pollStatus}}</p>
                </b-col>

                <b-col
                    align-self="center">
                    <p v-show="poll.status !== 'EDITING'"
                    ><span class="participants">Participants: </span>{{poll.pollEntries}}</p>
                </b-col>

                <b-col cols="4" style="text-align: end">
                <span @click="loadTo" class="configLink">Setup
                </span>
                </b-col>
                <b-col cols="2">
                    <b-button variant="primary"
                              @click="copyPoll">
                        Copy Poll
                    </b-button>
                </b-col>
            </b-row>
        </b-card-body>
        -->


    </b-card>
</template>

<script>
    import {mapActions} from "vuex";

    export default {

        name: "PollListElement",
        props: ["poll"],
        data() {
            return {
                pollStatus: '',
                iterations: [],
                fields: [
                    {key: 'id', sortable: true, label: 'ID'},
                    {key: 'status', sortable: true, label: 'Status'},
                    {key: 'start', label: 'Start Date'},
                    {key: 'end', label: 'End Date'},
                    {key: 'pollEntries', sortable: true, label: 'Entries'}
                ],
                dateTimeFormat: new Intl.DateTimeFormat('en', {
                    year: 'numeric',
                    month: 'short',
                    day: '2-digit',
                    weekday: 'short',
                    hour: 'numeric',
                    minute: 'numeric',
                    second: 'numeric'
                })
            }
        },
        beforeMount() {
            console.log('hallo ')
            if (this.poll.pollIterations !== []) {
                this.iterations = this.poll.pollIterations
                if (this.iterations !== undefined) {
                    this.iterations.reverse()
                    this.iterations.forEach(iteration => {
                        iteration.pollEntries = iteration.pollEntries.length
                        iteration.start = this.showDate(iteration.start)
                        iteration.end = this.showDate(iteration.end)

                    })
                }
            }

            console.log('byebye')
            console.log(this.poll.creationTime)
            switch (this.poll.status) {
                case 'EDITING':
                    this.pollStatus = 'editing';
                    break;
                case 'LAUNCHED':
                    this.pollStatus = 'launched';
                    break;
                case null:
                    this.pollStatus = ''
            }
        },
        methods: {
            ...mapActions('currentPoll', {
                loadPoll: "load"
            }),
            ...mapActions('myPolls', {
                duplicatePoll: "duplicate"
            }),
            async loadTo() {
                await this.loadPoll(this.poll.id)
                return this.$router.push({
                    name: 'poll-tabbed',
                    params: {
                        pollId: this.poll.id
                    }
                })
            },
            async copyPoll() {
                let newPoll = await this.duplicatePoll(this.poll.id);
                return this.$router.push({
                    name: 'edit-poll',
                    params: {
                        pollId: newPoll.id
                    }
                })
            },
            showDate(date) {
                return new Date(date)
            }
        },

    }
</script>

<style scoped>
    .configLink {
        font-size: 18px;
        color: #7F7E7F;
        cursor:pointer;
    }
    .configLink:hover {
        text-decoration: underline
    }
    .status {
        font-size: 18px;
        font-style: italic;
        color: #3eab37;
    }
    .participants {
        font-style: italic;
        color: #3eab37;
    }
</style>
