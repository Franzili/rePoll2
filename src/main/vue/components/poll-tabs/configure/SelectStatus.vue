<template>
    <b-card>
        <h6>Status</h6>

        <b-row class="align-items-center">
            <b-col>
                <b-form-radio-group
                    v-model="selected"
                    name="plain-inline">
                    <b-form-radio value="IN_PROCESS"
                                  :disabled="poll.status === 'READY'
                                || poll.status === 'ACTIVATED'
                                || poll.status === 'DEACTIVATED'">In Bearbeitung</b-form-radio>
                    <b-form-radio value="READY"
                                  :disabled="poll.status === 'ACTIVATED'
                                || poll.status === 'DEACTIVATED'">Bereit</b-form-radio>
                    <b-form-radio value="ACTIVATED"
                                  :disabled="poll.status === 'DEACTIVATED'">Aktiviert</b-form-radio>
                    <b-form-radio value="DEACTIVATED">Deaktiviert</b-form-radio>
                </b-form-radio-group>
            </b-col>

            <b-col cols="2">
                <b-button class="float-right" v-b-modal.modal-center>Apply</b-button>
                <b-modal v-if="isStatusChange()"
                         id="modal-center"
                         centered
                         title="Are you sure you want to change poll status?"
                         header-bg-variant="danger"
                         @ok="handleOk">
                    <p v-if="poll.status === 'IN_PROCESS'">
                        If you proceed you won't be able to change the degree of anonymity anymore
                        and the status can no longer be changed in a previous one.
                    </p>
                    <p class="my-4" v-else>
                        If you proceed the status can no longer be changed in a previos one.
                    </p>
                </b-modal>
            </b-col>
        </b-row>
    </b-card>
</template>

<script>
    import axios from "axios";
    import {mapGetters} from "vuex";

    export default {
        name: "SelectStatus",
        data() {
            return {
                tmpID: 0,
                selected: ''
            }
        },
        computed: {
            ...mapGetters(['getPoll']),
            poll() {
                return this.getPoll(this.tmpID)
            }
        },
        created: function () {
            this.tmpID = this.$route.params.tmpPollID;
        },
        methods: {
            isStatusChange() {
                if (this.selected === '') {
                    return false
                }
                return !(this.selected === this.poll.status)
            },
            handleOk() {
                this.poll.status = this.selected
                let pollCmd = this.poll
                console.log(this.poll.id)
                axios.put('/api/v1/polls/'+ this.poll.id + '/', pollCmd)
                    .then((response) => {
                        console.log(response.data)
                    }).catch((err) => {
                    console.log(err.message)
                })
            },
        }
    }
</script>

<style scoped>

</style>
