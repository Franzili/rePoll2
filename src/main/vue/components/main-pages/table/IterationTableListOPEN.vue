<template>

    <b-container>
        <b-row>
            <b-col>
                <!--
                <p>
                    <b-button variant="primary"
                              class="addButton"
                              v-b-modal.launchModal>Open Now</b-button>
                </p>
                -->
                <!--
                    dritte reihe in b-button
                              :disabled="iteration.status === 'OPEN'"
                              -->

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
            </b-col>
        </b-row>

        <b-row v-bind:key="iteration.id" v-for="iteration in iterations">
            <b-col>
                <p v-if="iteration.status === 'OPEN'">
                    <IterationTableElementOPEN v-bind:iteration="iteration"/>
                </p>
            </b-col>
        </b-row>
    </b-container>


</template>

<script>
    import IterationTableElementOPEN from "./IterationTableElementOPEN";
    export default {
        name: "IterationTableListOPEN",
        props: ["iterations"],
        data() {
            return {
                ende: "",
                beginn: "",
                launchState: "",
                dateEnde: ""
            }
        },
        methods: {
            getTimeNow: function() {
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
        },
        components: {IterationTableElementOPEN}
    }
</script>

<style scoped>

</style>
