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
            @ok="handleOk"><!--@ok="launchSingleIter()"> --><!--HIER EINGABE VON DATUM ERMÖGLICHEN ODER WAS ANDERS FÜR LAUFZEIT/ABLAUFDATUM!!!-->
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
                submittedDates: []
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
                if (!this.checkFormValidity()) {
                    return
                }
                this.launchSingleIter()
                this.$nextTick(() => {
                    this.$bvModal.hide('launchModal')
                })
            },
            launchSingleIter() {
                let pollIterationCmd = {
                    start: LocalDateTime.now(),
                    end: this.ende,
                    status: 'ACTIVATED'
                }
                this.updateIter(pollIterationCmd);
                let pollCmd = {
                    id: this.pollId,
                    status: 'READY'
                }
                this.updatePoll(pollCmd);
            },
            getLocalDate() {
                LocalDateTime a  LocalDateTime.now();
            }
            /*launchPoll() { //Button Launch: launch itetation now
                //TODO add iteration from this moment to open end
                this.poll.status = "ACTIVATED"
                let pollCmd = {
                    id: this.poll.id,
                    status: this.poll.status
                };
                this.updatePoll(pollCmd);
            }*/
        }
    }
</script>

<style scoped>

</style>
