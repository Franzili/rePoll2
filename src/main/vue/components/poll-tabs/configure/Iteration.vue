<template>
    <b-card v-if="poll.status === 'IN_PROCESS'">
        <h6>Iteration</h6>

        <p>
            If you launch this poll, you will not be able to edit it again!
        </p>

        <p>
            <b-button class="float-right" variant="primary">Launch</b-button>
        </p>
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

    import {mapState} from "vuex";

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

        }
    }
</script>

<style scoped>

</style>
