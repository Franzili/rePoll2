<template>
    <b-card>
        <b-button variant="danger"
                  class="float-right"
                  v-b-modal.modal-1
        >Delete Poll</b-button>
        <b-modal id="modal-1"
                 centered
                 title="Are you sure you want to delete this poll?"
                 @ok="deletePoll">
        </b-modal>
    </b-card>
</template>

<script>
    import {mapGetters} from "vuex";
    import axios from "axios";

    export default {
        name: "DeletePoll",
        data() {
            return {
                tmpID: 0,
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
        }
    }
</script>

<style scoped>

</style>
