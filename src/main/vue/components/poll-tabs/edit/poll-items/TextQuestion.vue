<template>
    <div :style="'font-family:' + poll.design.font + ';color:' + poll.design.textColour">
        <b-form-input v-if="!editing"
                      :maxlength="model.charLimit"
                      :placeholder="editable ? 'Enter some text...' : ''"
                      :disabled="editable"
                      v-model="answerText"
                      class="text-box"/>

        <p v-else>
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
        </p>
    </div>
</template>

<script>
    import {mapState} from "vuex";

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
