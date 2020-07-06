<template>
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
                <b-col style="text-align: end">
                    <b-button-toolbar class="float-right">
                        <b-form-group>
                            <b-button-group size="sm">
                                <b-button
                                    @click="loadTo('poll-tabbed')"
                                    variant="outline-secondary"
                                    title="Setup">
                                    <b-icon icon="gear-fill"></b-icon>
                                </b-button>
                                <b-button
                                    @click="loadTo('poll-stats')"
                                    :disabled="poll.status === 'EDITING'"
                                    variant="outline-secondary"
                                    title="Analysis">
                                    <b-icon icon="graph-up"></b-icon>
                                </b-button>
                            </b-button-group>
                        </b-form-group>
                    </b-button-toolbar>
                </b-col>
            </b-row>

            <b-row class="align-items-center">
                <b-col>
                    <h6 class="text-muted">Status:</h6>
                    <strong style="color: #3eab37;">{{this.pollStatus}}</strong>
                </b-col>
                <b-col class="text-muted">
                    <h6>Creation Date:</h6>
                    {{(dateTimeFormat.format(new Date(poll.creationTime)))}}
                </b-col>
                <b-col class="text-muted">
                    <h6>Creator:</h6>
                    {{poll.creator.username}}
                </b-col>
                <b-col class="text-muted">
                    <h6>Participants:(total)</h6>
                    <span v-if="poll.status === 'EDITING'">N/A</span>
                    <span v-else>{{participants}}</span>
                </b-col>
            </b-row>

            <b-container>
                <IterationSlide v-if="poll.pollIterations.length > 0 && poll.status === 'LAUNCHED'"
                    v-bind:current="current"
                    v-bind:iteration-data="iterationData"></IterationSlide>
            </b-container>
        </b-card-body>

    </b-card>
</template>

<script>
    import IterationSlide from "./IterationSlide";
    import {mapActions} from "vuex";

    export default {

        name: "PollListElement",
        props: ["poll"],
        data() {
            return {
                pollStatus: '',
                iterationData: {},
                participants: 0,
                current: null,
                dateTimeFormat: new Intl.DateTimeFormat('en', {
                    year: 'numeric',
                    month: 'short',
                    day: '2-digit',
                    weekday: 'short',
                    hour: 'numeric',
                    minute: 'numeric',
                })
            }
        },
        created() {
            if (this.poll.status === 'LAUNCHED' && this.poll.currentIteration !== null) {
                this.current = true;
                this.iterationData = this.poll.currentIteration
            } else if (this.poll.status === 'LAUNCHED'){
                this.current = null;
                this.iterationData = this.getLastIteration(this.poll.pollIterations);
            }
            if (this.poll.status === 'LAUNCHED') {
                this.poll.pollIterations.forEach(iteration => {
                    this.participants = this.participants + iteration.pollEntries
                })
            }

        },
        beforeMount() {
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
            async loadTo(adr) {
                await this.loadPoll(this.poll.id)
                return this.$router.push({
                    name: adr,
                    params: {
                        pollId: this.poll.id
                    }
                })
            },
            getLastIteration(iterations) {
                let res = null;
                iterations.forEach(iteration => {
                    if (res === null) {
                        res = iteration
                    } else {
                        if (new Date(res.end) - new Date(iteration.end) < 0) {
                            res = iteration
                        }
                    }
                })
                return res;
            }
        },
        components: {
            IterationSlide
        }

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
    .participants {
        font-style: italic;
        color: #3eab37;
    }
</style>
