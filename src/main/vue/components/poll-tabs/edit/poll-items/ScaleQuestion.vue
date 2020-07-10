<template>
    <div
        :style="'font-family:' + poll.design.font + ';color:' + poll.design.textColour"
        v-if="!editing">
        <div class="scale-question-legend">
            <span class="scale-question-legend-left">{{model.scaleNameLeft}} ({{model.min}})</span>
            <span class="scale-question-legend-spacer"></span>
            <span class="scale-question-legend-left">{{model.scaleNameRight}} ({{model.max}})</span>
        </div>
        <b-form-input
            id="bg-opacity"
            v-model="selected"
            type="range"
            number
            :min="model.min"
            :max="model.max"
            :step="model.stepCount"
            :disabled="editable"
        ></b-form-input>
        <div>
            <b>Value:</b> {{ selected }}
        </div>
        <p/>
    </div>

    <div v-else>
        <b-form name="scaleConfiguration">
            Min:
            <b-form-input type="number" name="min" value="" v-on:keyup="minIsNumber" v-model="model.min"></b-form-input>
            Max:
            <b-form-input type="number" name="max" v-on:keyup="maxIsNumber" v-model="model.max"></b-form-input>
            Step Size:
            <b-form-input type="number" name="step" v-on:keyup="stepIsNumber" v-model="model.stepCount"></b-form-input>
            Minimum Label:
            <b-form-input v-model="model.scaleNameLeft"></b-form-input>
            Maximum Label:
            <b-form-input v-model="model.scaleNameRight"></b-form-input>
        </b-form>
    </div>
</template>

<script>
    import {mapState} from "vuex";

    export default {
        name: "ScaleQuestion",
        data() {
            return {
                maxValid: this.model.min < this.model.max,
                stepValid: true,

                // start value is the average of min and max, rounded to the
                // nearest multiple of stepCount
                selected: Math.round(((this.model.min + this.model.max) / 2)
                    / this.model.stepCount) * this.model.stepCount
            }
        },

        methods: {
            minIsNumber() {
                if (document.scaleConfiguration.min.value <"0" ||
                    document.scaleConfiguration.min.value > "9") {
                    document.scaleConfiguration.min.value = "1";
                    return false
                }
                return true
            },
            maxIsNumber() {
                console.log("jap")
                if (document.scaleConfiguration.max.value <"0" ||
                    document.scaleConfiguration.max.value > "9") {
                    document.scaleConfiguration.max.value = this.model.min;
                    return false
                }
                return true
            },
            stepIsNumber() {
                console.log("jap")
                if (document.scaleConfiguration.step.value <"0" ||
                    document.scaleConfiguration.step.value > "9") {
                    document.scaleConfiguration.step.value = "1";
                    return false
                }
                return true
            },

        },
        computed: {
            answer: function() {
                return {
                    type: "ScaleAnswer",
                    scaleNumber: this.selected
                }
            },
            ...mapState('currentPoll', {
                poll: 'poll',
            })
        },
        props: {
            model: {
                type: Object,
                required: true
            },
            editable: {
                type: Boolean,
                required: false,
                default: true
            },
            editing: {
                type: Boolean,
                required: false,
                default: false
            }
        },
        watch: {
            model: {
                handler: function() {
                    this.$emit('modelChanged', this.model)
                },
                deep: true
            }
        }
    }
</script>

<style lang="scss" scoped>
    .scale-question-legend {
        display: flex;
    }
    .scale-question-legend-spacer {
        flex-grow: 1;
    }
</style>
