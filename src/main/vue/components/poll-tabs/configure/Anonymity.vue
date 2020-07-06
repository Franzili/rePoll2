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


        <b-card v-if="poll.status === 'EDITING'">
            <h6>Anonymity</h6>

            <b-form-radio-group
                v-model="anonymityChecked">

                <b-form-radio value="ANONYMOUS">
                    anonymous<br>
                    <small class="text-muted">
                        The participants are unknown. You get one link for all participants.
                    </small>
                </b-form-radio>
                <b-form-radio value="PSEUDONYMOUS">
                    pseudonymous<br>
                    <small class="text-muted">
                        There is minimal data transfer.
                        Except for a technical key, that acts as a pseudonym, no data of the participants get saved.
                        They get different links, but you cannot track the singular participant.
                    </small>
                </b-form-radio>
                <b-form-radio value="NON_ANONYMOUS">
                    non-anonymous<br>
                    <small class="text-muted">
                        The participants are known. Each of them gets a personalized link to
                        take part in the poll.
                    </small>
                </b-form-radio>
            </b-form-radio-group>

            <b-modal ref="modalAnonymity"
                     centered
                     @ok="changeAnonymity()"
                     @close="dontChangeAnonymity()"
                     @cancel="dontChangeAnonymity()">
                <p>
                    Are you sure you want to change poll anonymity?
                </p>
            </b-modal>

        </b-card>
    </div>
</template>

<script>
    import {mapState, mapActions} from "vuex";

    export default {
        name: "Anonymity",
        data() {
            return {
                anonymityChecked: ''
            }
        },
        computed: {
            ...mapState('currentPoll', {
                poll: 'poll',
            })
        },
        watch: {
            anonymityChecked() {
                if(this.anonymityChecked !== this.poll.anonymity) {
                    this.$refs.modalAnonymity.show()
                }
            }
        },
        created: function() {
            this.anonymityChecked = this.poll.anonymity;
        },
        methods: {
            ...mapActions('currentPoll', {
                updatePoll: 'update'
            }),
            changeAnonymity() {
                this.poll.anonymity = this.anonymityChecked;
                let pollCmd = {
                    id: this.poll.id,
                    anonymity: this.poll.anonymity
                };
                this.updatePoll(pollCmd);
            },
            dontChangeAnonymity() {
                this.anonymityChecked = this.poll.anonymity
            }
        }
    }
</script>

<style scoped>

</style>
