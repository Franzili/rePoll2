<template>
    <div :style="'font-family:' + poll.design.font + ';color:' + poll.design.textColour">
        <b-form-checkbox-group v-if="!editing"
                               v-model="selected"
                               :disabled="editable">
            <div v-bind:key="choice.id" v-for="choice in model.choices">
                <b-form-checkbox :value="choice.id">{{choice.text}}</b-form-checkbox>
            </div>
            <template v-if="model.numberOfBonusChoices !== 0 && !editable">
                <div v-bind:key="choice.id" v-for="choice in customChoices">
                <span class="choice-item">
                    <b-input v-model="choice.text"></b-input>

                    <b-button size="sm"
                              pill
                              variant="outline-secondary"
                              @click="deleteChoice(choice.id)">
                        <b-icon-trash-fill/>
                    </b-button>
                </span>
                </div>
                <div>
                    <b-button v-if="customChoices.length !== model.numberOfBonusChoices"
                              size="sm"
                              @click="addChoice">
                        <b-icon-plus></b-icon-plus>
                    </b-button>
                </div>
            </template>
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
    import {mapState} from "vuex";
    export default {
        name: "MultiChoiceQuestion",
        components: {ChoiceEditor},
        data() {
            return {
                selected: [],
                bonusChoices: 0,
                customChoices: []
            }
        },
        computed: {
            answer: function() {
                return {
                    type: "MultiChoiceAnswer",
                    choiceIds: this.selected, //.map(choice => choice.id)
                    bonusChoices: this.customChoices
                }
            },
            ...mapState('currentPoll', {
                poll: 'poll',
            })
        },
        methods: {
            addChoice() {
                if (this.customChoices.length < this.model.numberOfBonusChoices && this.model.numberOfBonusChoices !== 0) {
                    let newChoiceId = Date.now()
                    this.customChoices.push({
                        text: "",
                        id: newChoiceId
                    })
                }
            },
            deleteChoice(choiceId) {
                this.customChoices = this.customChoices.filter(choice => choice.id !== choiceId)
            }
        },
        watch: {
            bonusChoices: function (newNumber) {
                this.model.numberOfBonusChoices = newNumber
            },
            model: {
                handler: function() {
                    this.$emit('modelChanged', this.model)
                },
                deep: true
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
