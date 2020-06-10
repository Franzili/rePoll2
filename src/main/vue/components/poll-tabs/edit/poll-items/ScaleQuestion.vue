<template>
    <div>
        <div class="slider">
            {{ selected }}
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

        <!--<b-icon-pencil class="my-icon" scale="1.5" v-if="edit && !editSlider" @click="changeEditSlider"></b-icon-pencil>-->

        <div v-if="editing">
            Min:
            <b-form-input v-model="model.min"></b-form-input>
            Max:
            <b-form-input v-model="model.max"></b-form-input>
            Step Size:
            <b-form-input class="my-form" v-model="model.stepCount"></b-form-input>

            <b-icon-check-all class="my-icon" scale="2" animation="fade" @click="changeEditSlider"></b-icon-check-all>
        </div>
    </div>
</template>

<script>
    export default {
        name: "ScaleQuestion",
        data() {
            return {
                selected: this.model.min
            }
        },
        computed: {
            answer: function() {
                return {
                    type: "ScaleAnswer",
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
    }
</script>

<style lang="scss" scoped>
</style>
