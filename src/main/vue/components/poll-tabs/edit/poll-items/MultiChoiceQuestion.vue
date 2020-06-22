<template>
    <div>
        <b-form-checkbox-group v-if="!editing"
                               v-model="selected"
                               :disabled="editable">
            <div v-bind:key="choice.id" v-for="choice in model.choices">
                <b-form-checkbox :value="choice.id">{{choice.text}}</b-form-checkbox>
            </div>
        </b-form-checkbox-group>

        <div v-else>
            <h6>Choices:</h6>
            <ChoiceEditor :choices="model.choices"
                          v-on:choicesChanged="model.choices = $event" />
            <h6>Number of custom choices allowed:</h6>
            <b-input v-model="bonusChoices"></b-input>
        </div>
    </div>
</template>

<script>
    import ChoiceEditor from "./ChoiceEditor";
    export default {
        name: "MultiChoiceQuestion",
        components: {ChoiceEditor},
        data() {
            return {
                selected: [],
                bonusChoices: 0
            }
        },
        computed: {
            answer: function() {
                return {
                    type: "MultiChoiceAnswer",
                    choiceIds: this.selected //.map(choice => choice.id)
                }
            },
        },
        watch: {
          bonusChoices: function (newNumber) {
              this.model.numberOfBonusChoices = newNumber
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
