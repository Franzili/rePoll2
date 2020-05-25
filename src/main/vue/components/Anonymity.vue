<template>
    <!-- anonymity -->
    <b-row align-h="center"
           v-if="poll.status === 'IN_PROCESS'">

        <div align="left">
            <div align="left">
                <h4>Anonymity</h4>
            </div>
            <b-form-group >
                <b-form-radio-group
                    v-on:input="changeAnonymityQuestion"
                    v-model="anonymityChecked"
                    stacked >
                    <b-form-radio value="ANONYMOUS" :disabled="waitingForConfirmation">
                        anonymous<br>
                        <small class="text-muted">
                            The participants are unknown and there are no data
                            about them. You get one link for all participants.
                        </small>
                    </b-form-radio>
                    <b-form-radio value="PARTIALLY_ANONYMOUS" :disabled="waitingForConfirmation">
                        partially anonymous<br>
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
            </b-form-group>
            <!-- Warning box for changes -->
            <b-alert class="alert-info" role="alert" v-model="sureToChangeAnonymity">
                <strong>Warning!</strong>
                Are you sure that you want to change the level of anonymity to {{anonymityChecked}}?
                <b-button @click="changeAnonymity" class="my-button">yes</b-button>
                <b-button @click="dontChangeAnonymity" class="my-button">no</b-button>
            </b-alert>
        </div>
        <p>{{anonymityChecked}} <br> {{this.poll.anonymity}}</p>

    </b-row>

</template>

<script>
    import axios from "axios";

    export default {
        name: "Anonymity",
        props: ["poll"],
        data() {
            return {
                anonymityChecked: this.poll.anonymity,
                sureToChangeAnonymity: false,
                waitingForConfirmation: false
            }
        },
        methods: {
            changeAnonymityQuestion: function () {
                console.log("Hallo!")
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
                console.log("change")
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
        margin-left: 4vw;
        margin-right: 0vw;
    }
</style>
