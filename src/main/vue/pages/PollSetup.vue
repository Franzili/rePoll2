<template>
    <div class="my-back">
        <div class="my-sh">

            <div>
                <b-button class="my-button" variant="secondary-outline">Bearbeiten</b-button>
                <b-button class="my-button">Analyse</b-button>
            </div>

            <b-row>
                <p style="margin-left: 20vw; margin-top: 2vh">Design</p>
            </b-row>

            <!-- Anonymity -->
            <b-container class="my-container2">
                <Anonymity v-bind:poll="poll"></Anonymity>
            </b-container>

            <b-row>
                <p style="margin-left: 20vw; margin-top: 2vh">Design</p>
            </b-row>

            <!-- Freaking Cool Radio Buttons -->
            <b-row align-h="center">
                <b-col>
                    <p>Status</p>
                    <p>{{ poll.status }}</p>
                </b-col>
                <b-col>
                    <div>
                        <b-form-group label="Raido v6.7">
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
                        </b-form-group>
                    </div>
                </b-col>
            </b-row>

            <b-row>
                <p>{{selected}}</p>
            </b-row>

            <!-- Warning Modal Component -->
            <b-row align-h="center">
                <div>
                    <b-button class="my-button2" v-b-modal.modal-center variant="primary">Anwenden</b-button>
                    <b-modal v-if="isStatusChange()"
                             id="modal-center"
                             centered
                             title="Warnung: Status wurde geändert!"
                             header-bg-variant="danger"
                             @ok="handleOk">
                        <p class="my-4">Wenn Sie den Status ändern kann er nicht mehr in einen
                            früheren Status gewechselt werden.</p>
                    </b-modal>
                <b-button class="my-button2" v-b-modal.modal-1>Delete Poll</b-button>
                        <b-modal id="modal-1"
                                 centered
                                 title="Are you sure you want to delete this poll?"
                                 @ok="deletePoll">
                        </b-modal>
                    </div>
            </b-row>
        </div>
    </div>
</template>

<script>

    import {mapActions, mapGetters} from "vuex";
    import Anonymity from "../components/poll-tabs/configure/Anonymity";
    import axios from 'axios';

    export default {
        name: "PollSetup",
        data() {
            return {
                tmpID: 0,
                selected: 'IN_PROCESS'
            }
        },
        created: function() {
            this.tmpID = this.$route.params.tmpPollID
            this.requestPoll(this.tmpID)
        },
        computed: {
            ...mapGetters(['getPoll']),
            poll() {
                return this.getPoll(this.tmpID)
            }
        },
        methods: {
            ...mapActions(['requestPoll']),
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
            // changed variable name to survey. and added response catches
            deletePoll() {
                let pollCmd = {title: this.poll.title, status: this.poll.status};
                axios.delete('/api/v1/polls/' + this.poll.id + '/', pollCmd)
                    .then((response) => {
                        console.log(response.data)
                        return this.$router.push('/polls')
                    }).catch((err) => {
                        console.log(err.message)
                    })
            },
        },

        components: {
            Anonymity
        },
    }
</script>

<style scoped>
</style>
