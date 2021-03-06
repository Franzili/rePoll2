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
                                    data-toggle="tooltip"
                                    @click="loadTo('poll-tabbed')"
                                    variant="outline-secondary"
                                    title="Go to the Configuration-Page">
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
                <IterationSlide v-if="poll.pollIterations.length > 0 && poll.status === 'LAUNCHED'
                    && !(new Date(iterationData.start) > Date.now())
                    && !this.mobile"
                                v-bind:current="current"
                                v-bind:iteration-data="iterationData"></IterationSlide>
                <IterationSlideMobile v-else-if="poll.pollIterations.length > 0 && poll.status === 'LAUNCHED'
                    && !(new Date(iterationData.start) > Date.now())"
                                      v-bind:current="current"
                                      v-bind:iteration-data="iterationData"></IterationSlideMobile>
            </b-container>
        </b-card-body>

    </b-card>
</template>

<script>
    import IterationSlide from "./IterationSlide";
    import IterationSlideMobile from "./IterationSlideMobile";
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
                }),
                window: {
                    width: 0,
                    height: 0
                },
                mobile: false
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
            window.addEventListener('resize', this.handleResize);
            this.handleResize();
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
        destroyed() {
            window.removeEventListener('resize', this.handleResize);
        },
        methods: {
            ...mapActions('currentPoll', {
                loadPoll: "load"
            }),
            isMobile() {
                if ( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
                    this.mobile = true
                    return true
                } else {
                    this.mobile = false
                    return false
                }
            },
            handleResize() {
                this.window.width = window.innerWidth;
                this.mobile = this.window.width < 1200;
            },
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
            IterationSlide,
            IterationSlideMobile
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
