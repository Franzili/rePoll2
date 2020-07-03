<template>
    <b-card>
        <h6>Launch</h6>
        <div class="d-flex align-items-center">
            <span class="text-muted flex-grow-1">
                If you launch the poll, you will not be able to edit it again.
            </span>
            <b-button class="button-spacing"
                      @click="launchAndScheduleNew">
                Schedule
            </b-button>
            <b-button variant="primary"
                      class="button-spacing"
                      @click="launchAndOpenNew">
                Launch now!
            </b-button>
        </div>
    </b-card>
</template>

<script>
    import {mapActions} from "vuex"

    export default {
        name: "Launch.vue",
        methods: {
            ...mapActions("currentPoll", {
                launch: "launch"
            }),
            ...mapActions("currentPoll/iterations", {
                scheduleNew: "scheduleNew",
                openNew: "openNew"
            }),

            async launchAndScheduleNew() {
                try {
                    await this.launch();
                    this.scheduleNew();
                } catch(err) {
                    console.warn(err);
                }
            },

            async launchAndOpenNew() {
                try {
                    await this.launch();
                    this.openNew();
                } catch(err) {
                    console.warn(err);
                }
            }
        }
    }
</script>

<style scoped>
    .button-spacing {
        margin-left: 5px;
    }

</style>
