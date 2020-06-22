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
            title="How long should the poll be activ ?"
            header-bg-variant="info"
            @ok="handleOk">
            <form ref="launchForm" @submit.stop.prevent="handleSubmit">
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
                        year-month-day hours:minutes:seconds
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
            <b-button class="float-right" variant="secondary">Schedule</b-button>
        </p>

    </b-card>

    <b-card v-else-if="poll.status !== 'IN_PROCESS'">
        <h6>Iteration</h6>

        <p>Comming soon</p>


    </b-card>
</template>

<script>
    //import {mapActions, mapState} from "vuex";

    import {mapActions, mapState} from "vuex";

    export default {
        name: "Iteration",
        data() {
            return {
                ende: '',
                launchState: null,
                timestamp: ""
            }

        },
        created() {

        },
        computed: {
            ...mapState('currentPoll', {
                poll: 'poll'
            }),
            ...mapState('myIterations', {
                iteration: 'iterations'
            })
        },
        methods: {
            ...mapActions('currentPoll', {
                updatePoll: 'update'
            }),
            ...mapActions('myIterations', {
                updateIter: 'update'
            }),
            getTimeNow: function() {
              const today = new Date();
                const date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
                const time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
                this.timestamp = date + ' ' + time;
            },
            checkFormValidity() {
                const valid = this.$refs.form.checkValidity()
                this.launchState = valid
                return valid
            },
            resetModal() {
                this.ende = ''
                this.launchState = null
            },
            handleOk(bvModalEvt) {
                bvModalEvt.preventDefault()
                this.handleSubmit()
            },
            handleSubmit() {
                /*if (!this.checkFormValidity()) {
                    return
                }*/
                this.ende = this.dateEnde
                this.launchSingleIter()
                this.$nextTick(() => {
                    this.$bvModal.hide('launchModal')
                })
            },
            launchSingleIter() {
                this.getTimeNow()
                let pollIterationCmd = {
                    start: this.timestamp,
                    end: this.ende,
                    status: 'ACTIVATED'
                }
                //this.updateIter(pollIterationCmd);
                console.log('start: ',pollIterationCmd.start)
                console.log('ende :', pollIterationCmd.end)
                console.log('status :', pollIterationCmd.status)
                /*let pollCmd = {
                    id: this.pollId,
                    status: 'READY'
                }
                this.updatePoll(pollCmd);*/
            }
        }
    }
</script>

<style scoped>

</style>
