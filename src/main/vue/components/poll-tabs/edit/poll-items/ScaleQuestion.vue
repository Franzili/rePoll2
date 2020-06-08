<template>
    <div v-if="!editing">
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
        ></b-form-input>
        <div>
            <b>Value:</b> {{ selected }}
        </div>
        <p/>
    </div>

    <div v-else>
        <b-form>
            Min:
            <b-form-input v-model="model.min"></b-form-input>
            Max:
            <b-form-input v-model="model.max"></b-form-input>
            Step Size:
            <b-form-input v-model="model.stepCount"></b-form-input>
            Minimum Label:
            <b-form-input v-model="model.scaleNameLeft"></b-form-input>
            Maximum Label:
            <b-form-input v-model="model.scaleNameRight"></b-form-input>
        </b-form>
    </div>
</template>

<script>
    export default {
        name: "ScaleQuestion",
        data() {
            return {
                // start value is the average of min and max, rounded to the
                // nearest multiple of stepCount
                selected: Math.round(((this.model.min + this.model.max) / 2)
                    / this.model.stepCount) * this.model.stepCount
            }
        },
        computed: {
            answer: function() {
                return {
                    scaleNumber: this.selected
                }
            }
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
