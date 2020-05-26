<template>
    <div>
        <p class="poll-mode-indicator">
            <i v-if="this.poll.anonymity === 'NON_ANONYMOUS'">
                Poll for known participants.</i>
            <i v-if="this.poll.anonymity === 'PSEUDONYMOUS'">
                Poll for pseudonymized participants.</i>
            <i v-if="this.poll.anonymity === 'ANONYMOUS'">
                Poll for anonymized participants.</i>
        </p>


        <b-card v-if="this.poll.status === 'IN_PROCESS'">
            <h6>Anonymity</h6>

            <b-form-radio-group
                v-on:input="changeAnonymityConfirmation"
                v-model="anonymityChecked"
                initial-anonymity-checked=this.poll.anonymity>

                <b-form-radio value="ANONYMOUS" :disabled="waitingForConfirmation">
                    anonymous<br>
                    <small class="text-muted">
                        The participants are unknown. You get one link for all participants.
                    </small>
                </b-form-radio>
                <b-form-radio value="PSEUDONYMOUS" :disabled="waitingForConfirmation">
                    pseudonymous<br>
                    <small class="text-muted">
                        Es sind nur minimal Daten (i.d.R. ein technischer Schlüssel) vom
                        Teilnehmer bekannt. Außer dem technischen Schlüssel werden keine
                        Teilnehmerdaten persistiert.
                    </small>
                </b-form-radio>
                <b-form-radio value="NON_ANONYMOUS" :disabled="waitingForConfirmation">
                    non-anonymous<br>
                    <small class="text-muted">
                        Der Teilnehmer ist bekannt. Es liegen Daten vor. Jeder Teilnehmer
                        erhält einen personalisierten Link.
                    </small>
                </b-form-radio>
            </b-form-radio-group>




                <b-alert class="alert-info" role="alert" align="center" v-model="sureToChangeAnonymity">
                    <strong>Warning!</strong>
                    Are you sure that you want to change the level of anonymity to {{anonymityChecked}}?<br>
                    <b-button @click="changeAnonymity" class="my-button">yes</b-button>
                    <b-button @click="dontChangeAnonymity" class="my-button">no</b-button>
                </b-alert>


        </b-card>
    </div>
</template>

<script>
    import axios from "axios";
    import {mapGetters} from "vuex";

    export default {
        name: "Anonymity",
        props: ["initialAnonoymityChecked"],
        data() {
            return {
                tmpID: 0,
                anonymityChecked: this.initialAnonymityChecked,
                sureToChangeAnonymity: false,
                waitingForConfirmation: false
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
            changeAnonymityConfirmation: function () {
                if (this.poll.anonymity !== this.anonymityChecked) {
                    this.sureToChangeAnonymity = true
                    this.waitingForConfirmation = true
                } else {
                    this.sureToChangeAnonymity = false
                }
            },
            changeAnonymity() {
                this.sureToChangeAnonymity = false
                this.poll.anonymity = this.anonymityChecked
                this.waitingForConfirmation = false
                let pollCmd = this.poll
                console.log(this.poll.id)
                axios.put('/api/v1/polls/'+ this.poll.id + '/', pollCmd)
                    .then((response) => {
                        console.log(response.data)
                    }).catch((err) => {
                    console.log(err.message)
                })
            },
            dontChangeAnonymity() {
                this.sureToChangeAnonymity = false
                this.anonymityChecked = this.poll.anonymity
                this.waitingForConfirmation = false
            }
        }
    }
</script>

<style scoped>
    .my-button {
        background-color: InfoBackground;
        font-size: 100%;
        color: black;
        margin-top: 1vw;
        margin-left: 4vw;
    }
</style>
