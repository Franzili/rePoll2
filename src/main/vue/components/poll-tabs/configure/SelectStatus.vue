<template>
    <b-card>
        <h6>Status</h6>

        <b-row class="align-items-center">
            <b-col>
                <b-form-radio-group
                    v-model="statusSelected"
                    name="plain-inline">
                    <b-form-radio value="IN_PROCESS"
                                  :disabled="this.pollStatus === 'READY'
                                || this.pollStatus === 'ACTIVATED'
                                || this.pollStatus === 'DEACTIVATED'">in process</b-form-radio>
                    <b-form-radio value="READY"
                                  :disabled="this.pollStatus === 'ACTIVATED'
                                || this.pollStatus === 'DEACTIVATED'">ready</b-form-radio>
                    <b-form-radio value="ACTIVATED"
                                  :disabled="this.pollStatus === 'DEACTIVATED'">activated</b-form-radio>
                    <b-form-radio value="DEACTIVATED">deactivated</b-form-radio>
                </b-form-radio-group>
            </b-col>

            <b-col cols="2">
                <b-button class="float-right" v-b-modal.modal-center>Apply</b-button>
                <b-modal v-if="this.pollStatus !== statusSelected"
                         id="modal-center"
                         centered
                         title="Are you sure you want to change poll status?"
                         header-bg-variant="danger"
                         @ok="doUpdate()">
                    <p v-if="this.pollStatus === 'IN_PROCESS'">
                        If you proceed you won't be able to change the degree of anonymity anymore
                        and the status can no longer be changed in a previous one.
                    </p>
                    <p class="my-4" v-else>
                        If you proceed the status can no longer be changed in a previous one.
                    </p>
                </b-modal>
            </b-col>
        </b-row>
    </b-card>
</template>

<script>
    import {mapActions, mapState} from "vuex";

    export default {
        name: "SelectStatus",
        data() {
            return {
                statusSelected: "",
            }
        },
        computed: {
            ...mapState('currentPoll', {
                pollId: state => state.poll.id,
                pollStatus: state => state.poll.status,
            }),
        },
        methods: {
            ...mapActions('currentPoll', {
                updatePoll: 'update'
            }),
            doUpdate() {
                let pollCmd = {
                    id: this.pollId,
                    status: this.statusSelected
                }
                this.updatePoll(pollCmd);
            }
        },
    }
</script>

<style scoped>

</style>
