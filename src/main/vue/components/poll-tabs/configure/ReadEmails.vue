<template>

    <div>
        <p>
            <b-form-textarea
                v-model="textareaMails"
                placeholder="Enter e-Mail addresses to invite."
                size="sm"
                rows="10"
            ></b-form-textarea>
        </p>
        <p>
            <b-button class="float-right"
                      data-toggle="tooltip"
                      title="Invite a new Participant"
                      variant="primary"
                      @click="inviteParticipants">Invite</b-button>
        </p>
    </div>
</template>

<script>
    import {mapActions, mapState} from "vuex";

    export default {
        name: "ReadEmails",
        data() {
            return {
                textareaMails: ''
            }
        },
        computed: {
            ...mapState('participants', {mailAnswer: 'mailAnswer'})
        },
        methods: {
            ...mapActions('participants', {
                create: "create"
            }),
            makeToast(message) {
                this.$bvToast.toast(message, {
                    title: 'Mail',
                    autoHideDelay: 10000,
                    appendToast: false
                })
            },
            async inviteParticipants() {
                var res = this.textareaMails.split(",");
                for (var pos in res) {
                    let participantCmd = {
                        fullName: '',
                        email: res[pos],
                    };
                    await this.create(participantCmd);
                    this.makeToast(this.mailAnswer);
                }
            },

        }
    }
</script>

<style scoped>

</style>
