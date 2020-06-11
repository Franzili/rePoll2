<template>
    <div>
        <!-- michaels variant with changes variables, works this way but is not a radio component in answer-->
        <b-form-group v-if="model.displayVariant === 'radio'">
            <div v-bind:key="choice.id" v-for="choice in model.choices">
                <b-form-radio v-model="selected" :value="choice.id">{{choice.text}}</b-form-radio>
                <b-button size="sm"
                          variant="outline-secondary"
                          pill v-if="editing"
                          @click="delPos(choice.id)">
                    <b-icon-trash-fill/>
                </b-button>
            </div>
        </b-form-group>

        <div v-if="model.displayVariant === 'dropdown'">
            <b-form-select v-model="selected">
                <b-form-select-option v-bind:key="choice.id"
                                      v-for="choice in model.choices"
                                      :value="choice.id">{{choice.text}}</b-form-select-option>
            </b-form-select>

            <div v-if="editing">
                <div v-bind:key="pos.id" v-for="pos in model.choices">
                    <b-container>
                        <b-row>
                            <b-col class="text-left" cols="8">{{pos.text}}</b-col>
                            <b-col><b-button class="del-pos-btn"
                                             size="sm"
                                             variant="outline-secondary"
                                             pill
                                             v-if="editing"
                                             @click="delPos(pos.id)">x</b-button></b-col>
                        </b-row>
                    </b-container>
                </div>
            </div>
        </div>
    </div>

</template>

<script>
    export default {
        name: "SingleChoiceQuestion",
        data() {
            return {
                selected: ""
            }
        },
        computed: {
            answer: function() {
                return {
                    type: "SingleChoiceAnswer",
                    choiceId: this.selected
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
