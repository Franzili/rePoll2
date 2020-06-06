<template>
    <div>
        <p class="poll-mode-indicator">
            <i v-if="poll.anonymity === 'NON_ANONYMOUS'">
                Poll for known participants.</i>
            <i v-if="poll.anonymity === 'PSEUDONYMOUS'">
                Poll for pseudonymized participants.</i>
            <i v-if="poll.anonymity === 'ANONYMOUS'">
                Poll for anonymized participants.</i>
        </p>


        <b-card v-if="poll.status === 'IN_PROCESS'">
            <h6>Anonymity</h6>

            <b-form-radio-group
                v-on:input="changeAnonymityConfirmation"
                v-model="anonymityChecked"
                initial-anonymity-checked=poll.anonymity>

                <b-form-radio value="ANONYMOUS" :disabled="waitingForConfirmation">
                    anonymous<br>
                    <small class="text-muted">
                        The participants are unknown. You get one link for all participants.
                    </small>
                </b-form-radio>
                <b-form-radio value="PSEUDONYMOUS" :disabled="waitingForConfirmation">
                    pseudonymous<br>
                    <small class="text-muted">
                        There is minimal data transfer.
                        Except for a technical key, that acts as a pseudonym, no data of the participants get saved.
                        They get different links, but you cannot track the singular participant.
                    </small>
                </b-form-radio>
                <b-form-radio value="NON_ANONYMOUS" :disabled="waitingForConfirmation">
                    non-anonymous<br>
                    <small class="text-muted">
                        The participants are known. Each of them gets a personalized link to
                        take part in the poll.
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
    import {mapState, mapActions} from "vuex";

    export default {
        name: "Anonymity",
        props: ["initialAnonymityChecked"],
        data() {
            return {
                anonymityChecked: this.initialAnonymityChecked,
                sureToChangeAnonymity: false,
                waitingForConfirmation: false
            }
        },
        computed: {
            ...mapState('currentPoll', {
                poll: 'poll'
            })
        },
        methods: {
            ...mapActions('currentPoll', {
                updatePoll: 'update'
            }),
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
                let pollCmd = {
                    id: this.poll.id,
                    anonymity: this.poll.anonymity
                }
                this.updatePoll(pollCmd);
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
