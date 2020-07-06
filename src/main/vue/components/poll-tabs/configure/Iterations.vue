<template>
    <b-card no-body>
        <b-list-group flush>
            <b-list-group-item>
                <h6 class="iteration-header">
                    Iterations
                </h6>
            </b-list-group-item>

            <!-- ===== CURRENT ITERATION ===== -->
            <b-list-group-item>
                <!-- if there is an iteration currently open -->
                <template v-if="current">
                    <span class="text-muted">
                        Currently open:
                    </span>
                    <b-list-group>
                        <IterationListElement :value="current"
                                              class="current-highlight"
                                              @update="onUpdate($event)"/>
                    </b-list-group>
                </template>

                <!-- else -->
                <template v-else>
                    <span class="text-muted">
                        No iteration is currently open.
                    </span>
                    <b-button variant="primary"
                              class="float-right"
                              @click="openNew">
                        Open new
                    </b-button>
                </template>
            </b-list-group-item>

            <!-- ===== SCHEDULED ITERATIONS ===== -->
            <b-list-group-item>
                <p class="d-flex">
                    <!-- if there are scheduled iterations -->
                    <template v-if="scheduled.length > 0">
                        <span class="text-muted align-self-center">
                            Scheduled:
                        </span>
                    </template>

                    <!-- else -->
                    <template v-else>
                        <span class="text-muted align-self-center">
                            No scheduled iterations.
                        </span>
                    </template>

                    <b-button class="ml-auto"
                              @click="scheduleNew">
                        Add
                    </b-button>
                </p>

                <b-list-group>
                    <IterationListElement v-for="item in scheduled"
                                          v-bind:key="item.id"
                                          @update="onUpdate($event)"
                                          :value="item" />
                </b-list-group>
            </b-list-group-item>

            <!-- ===== PREVIOUS ITERATIONS ===== -->
            <b-list-group-item>
                <!-- if there are previous iterations -->
                <template v-if="previous.length > 0">
                    <span class="text-muted">
                        Previous iterations:
                    </span>
                    <IterationListElement v-for="item in previous"
                                          v-bind:key="item.id"
                                          @update="onUpdate($event)"
                                          :value="item" />
                </template>

                <!-- else -->
                <template v-else>
                    <span class="text-muted">
                        No previous iterations.
                    </span>
                </template>

            </b-list-group-item>
        </b-list-group>

    </b-card>
</template>

<script>
    import {mapGetters, mapActions} from "vuex";
    import IterationListElement from "./IterationListElement";
    export default {
        name: "Iterations",
        components: {IterationListElement},
        data() {
            return {
                // ids to the timeouts that trigger a reload once a poll has been opened automatically
                timeoutIds: {}
            }
        },
        computed: {
            ...mapGetters("currentPoll/iterations", {
                current: "current",
                scheduled: "scheduled",
                previous: "previous"
            })
        },
        methods: {
            onUpdate(model) {
                this.update(model);
                let timeout = model.start.getTime() - Date.now();
                let timeoutId = setTimeout(() => {
                    delete this.timeoutIds[model.id];
                    location.reload();
                }, timeout);
                this.timeoutIds[model.id] = timeoutId;
            },
            ...mapActions("currentPoll/iterations", {
                openNew: "openNew",
                scheduleNew: "scheduleNew",
                update: "update"
            })
        },
        beforeDestroy() {
            for (let modelId in this.timeoutIds) {
                clearTimeout(this.timeoutIds[modelId]);
            }
        }
    }
</script>

<style lang="scss" scoped>
    @import "../../../assets/stylesheet";

    .current-highlight {
        border: 1px solid $primary !important;
    }

    .iteration-header {
        margin-bottom: 0 !important;
    }
</style>
