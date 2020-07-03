<template>
    <b-card no-body>
        <b-list-group flush>
            <b-list-group-item>
                <h6 class="iteration-header">
                    Iterations
                </h6>
            </b-list-group-item>

            <b-list-group-item>
                <span class="text-muted">
                    Currently open:
                    {{current}}
                    <b-list-group>
                        <IterationListElement :model="current"/>
                    </b-list-group>
                </span>
            </b-list-group-item>

            <b-list-group-item>
                <span class="text-muted">
                    Scheduled:
                    {{scheduled}}
                </span>
                <b-list-group>
                    <IterationListElement v-for="item in scheduled"
                                          v-bind:key="item.id"
                                          :model="item" />
                </b-list-group>
            </b-list-group-item>

            <b-list-group-item>
                <span class="text-muted">
                    Previous iterations:
                    {{previous}}
                </span>
                <IterationListElement v-for="item in previous"
                                      v-bind:key="item.id"
                                      :model="item" />
            </b-list-group-item>
        </b-list-group>

    </b-card>
</template>

<script>
    import {mapGetters} from "vuex";
    import IterationListElement from "./IterationListElement";
    //import IterationListElement from "./IterationListElement";
    export default {
        name: "Iterations",
        components: {IterationListElement},
        //components: {IterationListElement},
        computed: {
            ...mapGetters("currentPoll/iterations", {
                current: "current",
                scheduled: "scheduled",
                previous: "previous"
            })
        }
    }
</script>

<style scoped>
    .iteration-header {
        margin-bottom: 0 !important;
    }
</style>
