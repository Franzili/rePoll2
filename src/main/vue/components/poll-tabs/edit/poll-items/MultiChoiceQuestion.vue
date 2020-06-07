<template>
    <b-form-checkbox-group v-model="selected">
        <div v-bind:key="choice.id" v-for="choice in model.choices">
            <b-form-checkbox :value="choice.text">{{choice.text}}</b-form-checkbox>
            <b-button class="del-pos-btn"
                      size="sm"
                      variant="outline-secondary"
                      pill v-if="editable"
                      @click="delPos(choice.id)">x</b-button>
        </div>
    </b-form-checkbox-group>
</template>

<script>
    export default {
        name: "MultiChoiceQuestion",
        data() {
            return {
                selected: []
            }
        },
        computed: {
            answer: function() {
                return {
                    choices: this.selected.map(choice => choice.id)
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
