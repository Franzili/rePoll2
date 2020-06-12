<template>
    <b-card>
        <!-- TODO: adapt type names to backend terminology -->
        <b-row v-if="type === 'known'">

        </b-row>

        <div v-if="type === 'pseudo'">
            <h6>Invite new participants</h6>

            <b-row>
                <b-col cols="6">
                    <p>
                        <b-form-textarea
                            placeholder="Enter e-Mail addresses to invite."
                            size="sm"
                            rows="10"
                        ></b-form-textarea>
                    </p>
                    <p>
                        <b-button class="float-right" variant="primary">Invite</b-button>
                    </p>
                </b-col>

                <b-col cols="6">
                    <p>
                        Pseudonymized participants will be assigned
                        a random identifier that cannot be traced back to their
                        identity.
                    </p>
                    <p>
                        Pseudonymized participants cannot be un-invited.
                    </p>

                    <br/>
                    <br/>

                    <p>
                        <b-row class="align-items-center">
                            <b-col cols="6">
                                {{ n_participated }} participated, <br/>
                                {{ n_invites_pending }} invites pending.
                            </b-col>

                            <b-col cols="6">
                                <b-button class="float-right">Remind</b-button>
                            </b-col>
                        </b-row>
                    </p>

                </b-col>
            </b-row>
        </div>

        <b-row v-if="type === 'anonymous'">
            <b-row>
                <b-col cols="6">
                <h6>Invite new participants</h6>
                    <p>
                        <b-form-textarea
                            placeholder="Enter E-Mail addresses to invite."
                            size="sm"
                            rows="10"
                        ></b-form-textarea>
                    </p>
                    <p>
                        <b-button class="float-right" variant="primary">Invite</b-button>
                    </p>
                </b-col>

                <b-col cols="6">
                    <h6>Share a link</h6>
                    <b-card align="center">
                        it's me
                    </b-card>

                    <br/>
                    <br/>

                    <p>
                        If you add E-Mail addresses to the invite box, the participants will receive a generic link
                        to the poll automatically.
                    </p>
                    <p>
                        You also have the possibility to share the link above via other platforms.
                    </p>

                </b-col>
            </b-row>
        </b-row>
    </b-card>
</template>

<script>
    // import {mapState} from "vuex";
    export default {
        name: "ManageParticipants",

        data() {
            return {
                //TODO
                type: 'anonymous',
                id: 0,

                //TODO
                n_participated: 412,
                n_invites_pending: 32
            }
        /*},

        computed: {
            ...mapState('currentPoll', {
                poll: 'poll'
            })
        },

        created: function() {
            this.n_invites_pending = this.poll.id;*/
        }
    }
</script>

<style scoped>

</style>
