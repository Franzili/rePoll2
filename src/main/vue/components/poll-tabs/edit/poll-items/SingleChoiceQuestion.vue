<template>
    <div>
        <template v-if="!editing">
            <p>
                <b-form-group v-if="model.displayVariant === 'radio'"
                              class="radio-group"
                              :disabled="editable">
                    <div v-bind:key="choice.id" v-for="choice in model.choices">
                        <b-form-radio v-model="selected" :value="choice.id">{{choice.text}}</b-form-radio>
                    </div>
                </b-form-group>
            </p>

            <p v-if="model.displayVariant === 'dropdown'">
                <b-form-select v-model="selected"
                               :disabled="editable">
                    <b-form-select-option :value="null">Select an option</b-form-select-option>
                    <b-form-select-option v-bind:key="choice.id"
                                          v-for="choice in model.choices"
                                          :value="choice.id">{{choice.text}}</b-form-select-option>
                </b-form-select>
            </p>
        </template>

        <template v-else>
            <h6>Choices:</h6>
            <ChoiceEditor :choices="model.choices"
                          v-on:choicesChanged="model.choices = $event"/>

            <h6>Display Variant:</h6>
            <p>
                <b-form-select v-model="model.displayVariant">
                    <b-form-select-option value="dropdown">Drop-Down</b-form-select-option>
                    <b-form-select-option value="radio">Radio Buttons</b-form-select-option>
                </b-form-select>
            </p>
        </template>
    </div>

</template>

<script>
    import ChoiceEditor from "./ChoiceEditor";

    export default {
        name: "SingleChoiceQuestion",
        data() {
            return {
                selected: null
            }
        },
        computed: {
            answer: function() {
                return {
                    choice: this.selected.id
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
        components: {
            ChoiceEditor,
        }
    }
</script>

<style lang="scss" scoped>
    .radio-group {
        margin-bottom: 0;
    }
</style>
