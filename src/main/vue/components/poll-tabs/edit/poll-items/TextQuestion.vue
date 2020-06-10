<template>
    <div>
        <b-form-input :maxlength="model.charLimit" v-model="answerText" class="text-box"/>

        <div v-if="editing">
            <b-form-group>
                <b-row>
                    <b-col cols="3">
                        <label class="text-muted">Character Limit:</label>
                    </b-col>
                    <b-col>
                        <b-form-input id="charLimitInput"
                                      type="number"
                                      v-model="model.charLimit"
                                      class="form-control-sm align-content-center"
                        ></b-form-input>
                    </b-col>
                </b-row>
            </b-form-group>
        </div>
    </div>
</template>

<script>
    export default {
        name: "TextQuestion",
        data() {
            return {
                answerText: ""
            }
        },
        computed: {
            answer: function() {
                return {
                    type: "TextAnswer",
                    text: this.answerText
                }
            }
        },
        props: {
            model: {
                type: Object,
                required: true
            },
            editing: {
                type: Boolean,
                required: false,
                default: false
            },
            editable: {
                type: Boolean,
                required: false,
                default: true
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
</style>
