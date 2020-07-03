<template>
    <b-list-group-item class="list-item">
        <b-container fluid>
            <b-row align-v="center">
                <!-- open date -->
                <b-col cols="4">
                    <template v-if="model.status === 'SCHEDULED'">
                        <span class="text-muted">Open</span>
                        <div class="d-flex">
                            <b-form-datepicker :id="'open-date-' + model.id"
                                               class="flex-grow-1 date-spacing"
                                               v-model="openDate"
                                               size="sm"
                                               :date-format-options="{ year: 'numeric', month: 'short', day: '2-digit', weekday: 'short' }"
                                               placeholder=""/>
                            <b-form-timepicker :id="'open-time-' + model.id"
                                               class="flex-grow-1"
                                               v-model="openTime"
                                               size="sm"
                                               placeholder="" />
                        </div>
                    </template>

                    <template v-else-if="model.status === 'OPEN' ||
                                         model.status === 'CLOSED'">
                        <span class="text-muted">
                            Opened: <br/>
                            21.05.2020, 15:49
                        </span>
                    </template>

                    <template v-else>
                        ??? {{iterationStatus}}
                    </template>
                </b-col>


                <!-- close date -->
                <b-col cols="4">
                    <template v-if="model.status === 'SCHEDULED' ||
                                    model.status === 'OPEN'">
                        <div class="d-flex">
                            <span class="text-muted">Close</span>
                            <b-form-checkbox class="ml-auto">
                                Manually
                            </b-form-checkbox>
                        </div>

                        <div class="d-flex">
                            <b-form-datepicker :id="'close-date-' + model.id"
                                               class="flex-grow-1 date-spacing"
                                               ref="closeDatePicker"
                                               :date-format-options="{ year: 'numeric', month: 'short', day: '2-digit', weekday: 'short' }"
                                               size="sm"
                                               placeholder=""/>
                            <b-form-timepicker :id="'close-time-' + model.id"
                                               class="flex-grow-1"
                                               ref="closeTimePicker"
                                               size="sm"
                                               placeholder="" />
                        </div>
                    </template>

                    <template v-else-if="model.status === 'CLOSED'">
                        <span class="text-muted">
                            Closed: <br/>
                            21.05.2020, 15:49
                        </span>
                    </template>
                </b-col>

                <b-col cols="4">
                    <b-button class="float-right" size="sm">
                        <b-icon-trash />
                    </b-button>
                </b-col>
            </b-row>
        </b-container>
    </b-list-group-item>
</template>

<script>
    export default {
        name: "IterationListElement",
        props: ['model'],
        data() {
            return {
                closeManually: true
            }
        },
        computed: {
            openDate: {
                get() {
                    return this.model.start;
                },
                set(newValue) {
                    this.model.start.setFullYear(newValue.getFullYear());
                    this.model.start.setMonth(newValue.getMonth());
                    this.model.start.setDate(newValue.getDate());
                },
                deep: true
            },
            openTime: {
                get() {
                    let s = this.model.start;
                    return `${s.getHours()}:${s.getMinutes()}:${s.getSeconds()}`  // TODO: do we need zero padding here?
                },
                set(newValue) {
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
                    this.model.end.setFullYear(newValue.getFullYear());
                    this.model.end.setMonth(newValue.getMonth());
                    this.model.end.setDate(newValue.getDate());
                },
                deep: true
            },
            closeTime: {
                get() {
                    let s = this.model.end;
                    return `${s.getHours()}:${s.getMinutes()}:${s.getSeconds()}`  // TODO: do we need zero padding here?
                },
                set(newValue) {
                    let numbers = newValue.split(":").map(Number);
                    this.model.end.setHours(numbers[0]);
                    this.model.end.setMinutes(numbers[1]);
                    this.model.end.setSeconds(numbers[2]);
                },
                deep: true
            }

        },
        watch: {
            closeManually: function(newVal) {
                if (newVal === true) {
                    this.model.end = null;
                }
            }
        }
    }
</script>

<style lang="scss" scoped>
    @import '~bootstrap/scss/bootstrap.scss';

    .list-item {
        padding: 8px;
        border: 1px solid $border-color !important;
    }
    .item-column {
        margin: 0 5px;
    }
    .date-spacing {
        margin-right: 3px;
    }

</style>
