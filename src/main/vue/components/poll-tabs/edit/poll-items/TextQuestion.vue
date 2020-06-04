<template>
    <QuestionCard v-bind:title="model.title">
        <form>
            <b-form-input :maxlength="model.charLimit" v-model="answerText" class="text-box"/>

            <div v-if="editable">
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
        </form>
    </QuestionCard>
</template>

<script>
    import TextQuestionModel from "../../../../store/poll-item-models/TextQuestionModel";
    import QuestionCard from "./QuestionCard";

    export default {
        name: "TextQuestion",
        data() {
            return {
                editing: false,
                answerText: ""
            }
        },
        props: {
            model: {
                type: TextQuestionModel,
                required: true
            },
            editable: {
                type: Boolean,
                required: false,
                default: true
            }
        },
        components: {QuestionCard},
    }
</script>

<style lang="scss" scoped>
</style>
