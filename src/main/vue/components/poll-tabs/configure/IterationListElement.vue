<template>
    <b-list-group-item class="list-item">
        <b-container fluid class="list-item-container">
            <b-row align-v="center">
                <!-- open date -->
                <b-col cols="4">
                    <template v-if="model.status === 'SCHEDULED'">
                        <span class="text-muted">Open</span>
                        <div class="d-flex">
                            <b-form-datepicker :id="'open-date-' + model.id"
                                               class="flex-grow-1 date-spacing"
                                               v-model="openDate"
                                               value-as-date
                                               size="sm"
                                               :date-format-options="dateTimeFormat"
                                               :state="startValid ? null : false"
                                               :min="now"
                                               placeholder=""/>
                            <b-form-timepicker :id="'open-time-' + model.id"
                                               class="flex-grow-1"
                                               v-model="openTime"
                                               :state="startValid ? null : false"
                                               size="sm"
                                               placeholder="" />
                        </div>
                    </template>

                    <template v-else-if="model.status === 'OPEN' ||
                                         model.status === 'CLOSED'">
                        <span class="text-muted">
                            Opened: <br/>
                            {{dateTimeFormat.format(model.start)}}
                        </span>
                    </template>

                    <template v-else>
                        ??? {{model.status}}
                    </template>
                </b-col>


                <!-- close date -->
                <b-col cols="4">
                    <template v-if="model.status === 'SCHEDULED' ||
                                    model.status === 'OPEN'">
                        <div class="d-flex">
                            <span class="text-muted">Close</span>
                            <b-form-checkbox v-model="closeManually"
                                             class="ml-auto">
                                Manually
                            </b-form-checkbox>
                        </div>

                        <div class="d-flex">
                            <b-form-datepicker :id="'close-date-' + model.id"
                                               class="flex-grow-1 date-spacing"
                                               v-model="closeDate"
                                               value-as-date
                                               ref="closeDatePicker"
                                               :date-format-options="dateTimeFormat"
                                               size="sm"
                                               :disabled="closeManually"
                                               :min="now"
                                               :state="endValid ? null : false"
                                               placeholder=""/>
                            <b-form-timepicker :id="'close-time-' + model.id"
                                               class="flex-grow-1"
                                               v-model="closeTime"
                                               ref="closeTimePicker"
                                               size="sm"
                                               :disabled="closeManually"
                                               :state="endValid ? null : false"
                                               placeholder="" />
                        </div>
                    </template>

                    <template v-else-if="model.status === 'CLOSED'">
                        <span class="text-muted">
                            Closed: <br/>
                            {{dateTimeFormat.format(model.end)}}
                        </span>
                    </template>
                </b-col>


                <!-- ====== ACTION AREA ===== --->
                <b-col cols="4">
                    <b-button v-if="model.status === 'SCHEDULED'"
                              @click="remove(model.id)"
                              class="float-right" size="sm">
                        <b-icon-trash />
                    </b-button>
                    <b-button v-else-if="model.status === 'OPEN'"
                              @click="closeNow(model.id)"
                              class="float-right">
                        Close now
                    </b-button>
                    <b-button v-else-if="model.status === 'CLOSED'"
                              class="float-right">
                        View results
                    </b-button>
                    <span v-else>
                        ??? {{model.status}}
                    </span>
                </b-col>
            </b-row>
        </b-container>
    </b-list-group-item>
</template>

<script>
    import {mapActions, mapGetters} from "vuex"

    export default {
        name: "IterationListElement",
        props: ['value'],

        data() {
            return {
                model: this.value,
                now: new Date(),
                closeManually: this.value.end === null || this.value.end === undefined,
                dateTimeFormat: new Intl.DateTimeFormat('en', {
                    year: 'numeric',
                    month: 'short',
                    day: '2-digit',
                    weekday: 'short',
                    hour: 'numeric',
                    minute: 'numeric',
                    second: 'numeric'
                })
            }
        },

        methods: {
            ...mapActions("currentPoll/iterations", {
                update: "update",
                closeNow: "closeNow",
                remove: "remove"
            })
        },

        computed: {
            // ===== validation ===== //

            ...mapGetters("currentPoll/iterations", {
                otherIterations: "iterations"
            }),

            startValid() {
                let hasIntersections = false;

                for (let iter of this.otherIterations) {
                    // if this is our iteration, skip it
                    if (iter.id === this.model.id) continue;

                    // check for intersections where our start date is too early:
                    // other iteration:      +----------------+
                    // this iteration:                    +-----------+
                    if (this.model.start >= iter.start &&
                        iter.end != null && iter.end >= this.model.start) {
                        hasIntersections = true;
                        break;
                    }
                }

                return this.model.start > this.now &&                                   // is in future
                       this.model.end === null || this.model.start < this.model.end &&  // start and end are monotonic
                       !hasIntersections
            },

            endValid() {
                let hasIntersections = false;

                // if this.model.end is null, the backend will only close the iteration
                // if needed. Hence this is always correct.
                if (this.model.end === null) {
                    return true;
                }

                // Otherwise, check for intersections.
                else {
                    for (let iter of this.otherIterations) {
                        // if this is our iteration, skip it
                        if (iter.id === this.model.id) continue;

                        if (this.model.start <= iter.start &&
                            // Check for cases where our end date is too late:
                            //
                            //  other iteration:             +-----------------+
                            //  this iteration:    +---------------+
                            this.model.end >= iter.start) {
                            hasIntersections = true;
                            break;
                        }
                    }

                    return this.model.end > this.now &&         // is in future
                           this.model.start < this.model.end && // start and end are monotonic
                           !hasIntersections
                }
            },

            // ==== time handling ==== //

            openDate: {
                get() {
                    return this.model.start;
                },
                set(newValue) {
                    if (!this.model.start) {
                        this.model.start = new Date();
                    } else {
                        // needed to make property watching work
                        this.model.start = new Date(this.model.start);
                    }
                    this.model.start.setFullYear(newValue.getFullYear());
                    this.model.start.setMonth(newValue.getMonth());
                    this.model.start.setDate(newValue.getDate());
                },
                deep: true
            },

            openTime: {
                get() {
                    if (this.model.start) {
                        let s = this.model.start;
                        return `${s.getHours()}:${s.getMinutes()}:${s.getSeconds()}`  // TODO: do we need zero padding here?
                    } else {
                        return null;
                    }
                },
                set(newValue) {
                    if (!this.model.start) {
                        this.model.start = new Date();
                    } else {
                        // needed to make property watching work
                        this.model.start = new Date(this.model.start);
                    }
                    let numbers = newValue.split(":").map(Number);
                    this.model.start.setHours(numbers[0]);
                    this.model.start.setMinutes(numbers[1]);
                    this.model.start.setSeconds(numbers[2]);
                },
                deep: true
            },

            closeDate: {
                get() {
                    return this.model.end;
                },
                set(newValue) {
                    if (!this.model.end) {
                        this.model.end = new Date();
                    } else {
                        // needed to make property watching work
                        this.model.end = new Date(this.model.end);
                    }
                    this.model.end.setFullYear(newValue.getFullYear());
                    this.model.end.setMonth(newValue.getMonth());
                    this.model.end.setDate(newValue.getDate());
                },
                deep: true
            },

            closeTime: {
                get() {
                    if (this.model.end) {
                        let e = this.model.end;
                        return `${e.getHours()}:${e.getMinutes()}:${e.getSeconds()}`  // TODO: do we need zero padding here?
                    } else {
                        return null;
                    }
                },
                set(newValue) {
                    if (!this.model.end) {
                        this.model.end = new Date();
                    } else {
                        // needed to make property watching work
                        this.model.end = new Date(this.model.end);
                    }
                    let numbers = newValue.split(":").map(Number);
                    this.model.end.setHours(numbers[0]);
                    this.model.end.setMinutes(numbers[1]);
                    this.model.end.setSeconds(numbers[2]);
                },
                deep: true
            },
        },

        watch: {
            model: {
                handler: function(newVal) {
                    switch(this.model.status) {
                        case 'SCHEDULED':
                            if (this.startValid && this.endValid) {
                                this.update(newVal);
                            }
                            break;
                        case 'OPEN':
                            if (this.endValid) {
                                this.update(newVal);
                            }
                            break;
                        case 'CLOSED':
                            // A closed model should not change.
                            break;
                    }
                },
                deep: true
            },
            closeManually: function(newVal) {
                if (newVal === true) {
                    this.model.end = null;
                } else {
                    let newEnd = new Date(this.model.start)
                    newEnd.setHours(newEnd.getHours() + 1);
                    this.model.end = newEnd;
                }
            }
        }
    }
</script>

<style lang="scss" scoped>
    @import '~bootstrap/scss/bootstrap.scss';

    .list-item {
        padding: 12px;
        margin: 0 -12px;
        border: 1px solid $border-color !important;
    }
    .item-column {
        margin: 0 5px;
    }
    .date-spacing {
        margin-right: 3px;
    }
    .list-item-container {
        padding: 0;
    }

</style>
