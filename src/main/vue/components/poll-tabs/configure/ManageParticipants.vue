<template>
    <b-card>
        <!-- TODO: adapt type names to backend terminology -->
        <div v-if="poll.anonymity === 'NON_ANONYMOUS'">
            <h6>Participants:</h6>
            <b-row>
                <b-col cols="6">
                    <p>
                        <b-table
                            show-empty
                            small
                            sticky-header="true"
                            :items="this.participants"
                            :fields="fields"
                            :sort-desc.sync="sortDesc"
                            :sort-direction="sortDirection">
                            <template v-slot:cell(name)="row">
                                {{ row.value.fullName }}
                            </template>
                        </b-table>
                    </p>
                    <p>
                        <b-button
                            class="float-right"
                            variant="primary"
                            v-b-modal.newParticipant>
                            Invite New
                        </b-button>
                    </p>
                    <p>
                        <UploadParticipants></UploadParticipants>
                    </p>
                </b-col>

                <b-col cols="6">
                    <p>
                        Known participants will receive a custom link to the poll automatically, when they are invited.
                    </p>
                    <p>
                        <b-row class="align-items-center">
                            <b-col cols="6">
                                {{ n_participated }} participated, <br/>
                                {{ n_invites_pending }} invites pending.
                            </b-col>

                            <b-col cols="6">
                                <b-button class="float-right" v-on:click="reminder">Remind</b-button>
                            </b-col>
                        </b-row>
                    </p>
                </b-col>
            </b-row>
            <b-modal
                id="newParticipant"
                title="Invite"
                centered
                @ok="addParticipant">
                <div>
                    <b-row
                        class="justify-content-md-center"
                        style="margin-bottom: 3vh">
                        <b-col col lg="2">
                            Name:
                        </b-col>
                        <b-col col lg="6">
                            <b-form-input
                                v-model="name">
                            </b-form-input>
                        </b-col>
                    </b-row>
                    <b-row
                        class="justify-content-md-center"
                        style="margin-bottom: 3vh">
                        <b-col col lg="2">
                            E-Mail:
                        </b-col>
                        <b-col col lg="6">
                            <b-form-input v-model="eMail">
                            </b-form-input>
                        </b-col>
                    </b-row>
                </div>
            </b-modal>
        </div>


        <div v-if="poll.anonymity === 'PSEUDONYMOUS'">
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
                                <b-button class="float-right" v-on:click="reminder">Remind</b-button>
                            </b-col>
                        </b-row>
                    </p>

                </b-col>
            </b-row>
        </div>

        <b-row v-if="poll.anonymity === 'ANONYMOUS'">
            <b-container>
                <b-row align-h="between">
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
                            <b-card-text>
                                <a v-bind:href="link">
                                    {{ link }}
                                </a>
                            </b-card-text>
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
            </b-container>
        </b-row>
    </b-card>
</template>

<script>
    import {mapState, mapActions} from "vuex";
    import UploadParticipants from "./UploadParticipants";

    export default {
        name: "ManageParticipants",
        components: {UploadParticipants},

        data() {
            return {
                link: '',
                items: [],
                fields: [
                    { key: 'fullName', label: 'Fullname', sortable: true, sortDirection: 'desc' },
                    { key: 'email', label: 'Email', sortable: true, sortDirection: 'desc' }],
                sortDesc: false,
                sortDirection: 'asc',

                //TODO
                n_participated: 6,
                n_invites_pending: 32,

                // For a single participant
                name: '',
                eMail: '',

                participantMailPair: '',
                mailSentCounter: 0
            }
        },

        computed: {
            ...mapState('currentPoll', {
                poll: 'poll'}),
            ...mapState('participants', {
                participants: 'participants',
                mailAnswer: 'mailAnswer'
            })
        },


        methods: {
            ...mapActions('participants', {
                loadParticipant: 'loadParticipant'
            }),
            ...mapActions('participants', {
                create: "create",
                remind: "remind"
            }),
            async addParticipant() {
                let participantCmd = {
                    fullName: this.name,
                    email: this.eMail
                }
                await this.create(participantCmd)
                this.makeToast(this.mailAnswer)
            },
            /**
             * Sends a reminder email to every participant that did not participated until now.
             * If you try to send emails twice in one page mount, you get a toast that this is not smart :D
             */
            async reminder() {
                if (this.mailSentCounter > 0) {
                    this.makeToast("You have sent Reminders just a few moments ago!\n" +
                        "Reload the page and try again to sent them anyway")
                } else {
                    this.makeToast("Sending Mails ...")
                    await this.remind();
                    this.mailSentCounter++;
                    this.makeToast(this.mailAnswer)
                }
            },
            makeToast(message) {
                this.$bvToast.toast(message, {
                    title: 'Mail',
                    autoHideDelay: 10000,
                    appendToast: false
                })
            }
        },
        mounted() {
            this.loadParticipant(this.poll.id);
            //this.n_participated = this.poll.entries.length;
        },

        created: function() {
            let id = this.$route.params.pollId;
            let domain = window.location.origin;
            this.link = domain + '/poll/' + id + '/answer';
        },

    }
</script>

<style scoped>

</style>
