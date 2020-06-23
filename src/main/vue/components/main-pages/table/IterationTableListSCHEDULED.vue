<template>

    <b-container>
        <b-row>
            <b-col>
                <p>
                    <b-button variant="primary"
                              class="addButton"
                              v-b-modal.scheduleModal>Open Now</b-button>
                </p>

                <b-modal
                    id="scheduleModal"
                    ref="scheduleIterModal"
                    centered
                    title="For what time period should the poll be active ?"
                    header-bg-variant="info"
                    @ok="handleOkSchedule">
                    <form ref="scheduleForm" @submit.stop.prevent="handleSubmitLaunch">
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
            </b-col>
        </b-row>

        <b-row v-bind:key="iteration.id" v-if="iteration.status === 'OPEN'" v-for="iteration in iterations">
            <b-col>
                <p>
                    <IterationTableElementSCHEDULED v-bind:iteration="iteration"/>
                </p>
            </b-col>
        </b-row>
    </b-container>


</template>

<script>

    import {mapActions} from "vuex";
    import IterationTableElementSCHEDULED from "./IterationTableElementSCHEDULED";

    export default {
        name: "IterationTableListSCHEDULED",
        props: ["iterations"],
        data: {
            ende: "",
            beginn: "",
        },
        methods: {
            ...mapActions('myIterations', {
                createIteration: 'create',
                listIterations: 'load',
                deleteIteration: 'delete'
            }),
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
        },
        components: {
            IterationTableElementSCHEDULED
        }
    }
</script>

<style scoped>
</style>
