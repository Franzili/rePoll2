<template>
    <b-card v-if="poll.status === 'IN_PROCESS'">
        <h6>Iteration</h6>

        <p>
            If you launch this poll, you will not be able to edit it again!
        </p>

        <p>
            <b-button class="float-right" variant="primary" data-toggle="modal" data-target="#launchModal">Launch</b-button>
        </p>
        <launchModal
            id="modalLaunch"
            centered
            title="How long should the poll be activ ?"
            header-bg-variant="info"
            @ok="doUpdate()"> HIER EINGABE VON DATUM ERMÖGLICHEN ODER WAS ANDERS FÜR LAUFZEIT/ABLAUFDATUM!!!
        </launchModal>
        <p>
            <b-button class="float-right" variant="secondary">Schedule</b-button>
        </p>

    </b-card>

    <!--v-else-if="poll.status ==='READY'"--><!-- Was bedeuted Launched hier von beiden? -->
    <b-card v-else-if="poll.status === 'ACTIVATED'">
        <h6>Iteration</h6>

        <p>Comming soon</p>


    </b-card>
</template>

<script>
    //import {mapActions, mapState} from "vuex";

    import {mapActions, mapState} from "vuex";

    export default {
        name: "Iteration",
        data() {
            return {

            }

        },
        created() {

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
            /*launchPoll() { //Button Launch: launch itetation now
                //TODO add iteration from this moment to open end
                this.poll.status = "ACTIVATED"
                let pollCmd = {
                    id: this.poll.id,
                    status: this.poll.status
                };
                this.updatePoll(pollCmd);
            }*/
        }
    }
</script>

<style scoped>

</style>
