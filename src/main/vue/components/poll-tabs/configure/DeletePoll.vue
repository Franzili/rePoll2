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
    import {mapActions, mapState} from "vuex";

    export default {
        name: "DeletePoll",
        computed: {
            ...mapState('currentPoll', {
                poll: 'poll'
            })
        },
        methods: {
            ...mapActions('myPolls', {
                delete: 'delete',
            }),
            deletePoll() {
                this.delete(this.poll.id);
                return this.$router.push('/')
            },
        }
    }
</script>

<style scoped>

</style>
