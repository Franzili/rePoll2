<template>
    <b-card v-if="poll.status === 'IN_PROCESS'">
        <h6>Iteration</h6>

        <p>
            If you launch this poll, you will not be able to edit it again!
        </p>

        <p>
            <b-button class="float-right" variant="primary" v-b-modal.launchModal>Launch</b-button>
        </p>

        <b-modal
            id="launchModal"
            ref="launchIterModal"
            centered
            title="How long should the poll be active ?"
            header-bg-variant="info"
            @ok="handleOkLaunch">
            <form ref="launchForm" @submit.stop.prevent="handleSubmitLaunch">
                <b-form-group
                    :state="launchState"
                    label="Expiration Date for Iteration"
                    label-for="endDatumInput"
                    invalid-feedback="Expiration Date is required"
                >
                    <b-form-input
                        id="launch-date-input"
                        v-model="dateEnde"
                        :state="launchState"
                        label
                        required>
                    </b-form-input>
                    <label>
                        yyyy-MM-dd HH:mm:ss
                    </label>
                </b-form-group>
            </form>
        </b-modal>



        <!--
        <a href="#myModal" role="button" class="btn btn-default" data-toggle="modal">Launch demo modal</a>
        -->


        <!--
        <p>
            <b-button href="#launchModal" class="float-right" variant="primary" data-toggle="modal">Launch</b-button>
        </p>

        <div id="launchModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="launchModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h3 id="launchModalLabel">Modal header</h3>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-xs-6">
                                <div id="calendar"></div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
                        <button class="btn btn-primary">Save changes</button>
                    </div>
                </div>
            </div>
        </div>
    -->
        <!--@ok="launchSingleIter()"> --><!--HIER EINGABE VON DATUM ERMÖGLICHEN ODER WAS ANDERS FÜR LAUFZEIT/ABLAUFDATUM!!!-->
        <!--
        <p>
            <b-button class="float-right" variant="primary" v-b-modal.launchModal>Launch</b-button>
        </p>

        <b-modal
            id="launchModal"
            ref="launchIterModal"
            centered
            title="How long should the poll be activ ?"
            header-bg-variant="info"
            @ok="handleOk">
            <form ref="launchForm" @submit.stop.prevent="handleSubmit">
                <b-form-group
                    :state="launchState"
                    label="End-Datum"
                    label-for="endDatumInput"
                    invalid-feedback="Expiration Date is required"
                >
                 <b-form-input
                     id="launch-date-input"
                     v-model="date"
                     :state="launchState"
                     required></b-form-input>
                </b-form-group>
            </form>
        </b-modal>
        -->

        <p>
            <b-button class="float-right" variant="secondary" v-b-modal.scheduleModal>Schedule</b-button>
        </p>


        <b-modal
            id="scheduleModal"
            ref="scheduleIterModal"
            centered
            title="For what time period should the poll be active ?"
            header-bg-variant="info"
            @ok="handleOkSchedule">
            <form ref="scheduleForm" @submit.stop.prevent="handleSubmitSchedule">
                <b-form-group
                    :state="scheduleState"
                    label="Start and Expiration Date for Iteration"
                    label-for="endDatumInput"
                    invalid-feedback="Expiration Date is required"
                >
                    <b-form-input
                        id="schedule-start-input"
                        v-model="dateStart"
                        :state="scheduleState"
                        label
                        required>
                    </b-form-input>
                    <label>
                        yyyy-MM-dd HH:mm:ss
                    </label><b-form-input
                    id="schedule-end-input"
                    v-model="dateEnde"
                    :state="scheduleState"
                    label
                    required>
                </b-form-input>
                    <label>
                        yyyy-MM-dd HH:mm:ss
                    </label>
                </b-form-group>
            </form>
        </b-modal>


    </b-card>

    <b-card v-else-if="poll.status === 'READY' || poll.status === 'LAUNCHED'">
        <h6>Iteration</h6>


        <!--
                <b-container v-if="loaded"
                             class="my-container">
                    <b-row align-h="center">
                        <h6>Currently Open:</h6>
                    </b-row>
                    <b-row>
                        <p>
                            <b-button v-if="poll.status === 'LAUNCHED'" class="float-right" variant="primary" v-b-modal.launchModal>Open Now</b-button>
                        </p>
                        <b-col >
                            <IterationTableListOPEN v-bind:iterations="iterations"/>
                        </b-col>
                    </b-row>
                </b-container>

                <b-container v-if="loaded"
                             class="my-container">
                    <b-row align-h="center">
                        <h6>Planned:</h6>
                    </b-row>
                    <b-row>
                        <p>
                            <b-button class="float-right" variant="secondary" v-b-modal.scheduleModal>New</b-button>
                        </p>
                        <b-col >
                            <IterationTableListSCHEDULED v-bind:iterations="iterations"/>
                        </b-col>
                    </b-row>
                </b-container>
                <b-container v-if="loaded"
                             class="my-container">
                    <b-row align-h="center">
                        <h6>Previous Iterations:</h6>
                    </b-row>
                    <b-row>
                        <b-col >
                            <IterationTableListCLOSED v-bind:iterations="iterations"/>
                        </b-col>
                    </b-row>
                </b-container>
        -->

        <b-container
                     class="my-container">
            <b-row align-h="center">
                <h6>Currently Open:</h6>
            </b-row>
            <b-row>
                <p>
                    <b-button v-if="poll.status === 'LAUNCHED'" class="float-right" variant="primary" v-b-modal.launchModal>Open Now</b-button>
                </p>
                <p>
                    <b-button class="float-right" variant="secondary" v-b-modal.scheduleModal>New</b-button>
                </p>
                <b-col >
                    <IterationTableList v-bind:iterations="iterations"/>
                </b-col>
            </b-row>
        </b-container>



    </b-card>
</template>

<script>

    import {mapActions, mapState} from "vuex";
    //import IterationTableListOPEN from "../../main-pages/table/IterationTableListOPEN";
    //import IterationTableListSCHEDULED from "../../main-pages/table/IterationTableListSCHEDULED";
    //import IterationTableListCLOSED from "../../main-pages/table/IterationTableListCLOSED";
    import IterationTableList from "../../main-pages/table/IterationTableList";

    export default {
        name: "Iteration",
        //components: {IterationTableListCLOSED, IterationTableListSCHEDULED, IterationTableListOPEN},
        components: {IterationTableList},
        data() {
            return {
                ende: "",
                beginn: "",
                launchState: null,
                timestamp: "",
                end: "",
                start: "",
                iterId: "",
                iterStatus: "",
                dateEnde: "",
                scheduleState: "",
                dateStart: "",
                loaded: false
            }
        },
        /*async mounted() {
            this.loaded = false
            await this.listIterations();
            this.loaded = true
        },*/
        created() {
            /*this.iterId = this.iterationId,
            this.iterStatus = this.iterationStatus,
            this.end = this.iterationEnd,
            this.start = this.iterationStart*/
        },
        computed: {
            ...mapState('currentPoll', {
                poll: 'poll'
            }),
            ...mapState('myIterations', {
                iterations: 'iterations'
            }),
            ...mapState('currentPoll', {
                pollId: state => state.poll.id,
                pollStatus: state => state.poll.status,
            }),
        },
        methods: {
            ...mapActions('currentPoll', {
                updatePoll: 'update'
            }),
            ...mapActions('myIterations', {
                createIteration: 'create',
                listIterations: 'load',
                deleteIteration: 'delete'
            }),
            getTimeNow: function() {
                //const today = new Date(); //yyyy-MM-dd HH:mm:ss
                //const date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
                //const time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
                //this.timestamp = date + ' ' + time;
                this.timestamp = new Date();
            },
            handleOkLaunch(bvModalEvt) {
                bvModalEvt.preventDefault()
                this.handleSubmitLaunch()
            },
            handleSubmitLaunch() {
                this.ende = this.dateEnde //ignore error 'unresolved variable'
                this.launchSingleIter()
                this.$nextTick(() => {
                    this.$bvModal.hide('launchModal')
                })
            },
            launchSingleIter() {
                this.getTimeNow()
                let pollIterationCmd = {
                    start: this.timestamp,
                    end: new Date(this.ende),
                    status: 'OPEN'
                }
                this.createIteration(pollIterationCmd);
                let pollCmd = {
                    id: this.pollId,
                    status: 'LAUNCHED' //'READY'
                }
                this.updatePoll(pollCmd);
            },
            handleOkSchedule(bvModalEvt) {
                bvModalEvt.preventDefault()
                this.handleSubmitSchedule()
            },
            handleSubmitSchedule() {
                this.ende = this.dateEnde //ignore error 'unresolved variable'
                this.beginn = this.dateStart
                this.scheduleSingleIter()
                this.$nextTick(() => {
                    this.$bvModal.hide('scheduleModal')
                })
            },
            scheduleSingleIter() {
                this.getTimeNow()
                let pollIterationCmd = {
                    start: new Date(this.beginn),
                    end: new Date(this.ende),
                    status: 'SCHEDULED'
                }
                this.createIteration(pollIterationCmd);
                let pollCmd = {
                    id: this.pollId,
                    status: 'LAUNCHED' //'READY'
                }
                this.updatePoll(pollCmd);
            }
        }
    }
</script>

<style scoped>

</style>
