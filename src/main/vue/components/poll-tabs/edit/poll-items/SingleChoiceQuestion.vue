<template>
    <QuestionCard v-bind:title="model.title">
        <form>
            <!-- michaels variant with changes variables, works this way but is not a radio component in answer-->
            <b-form-group v-if="question.displayVariant === 'radio'">
                <div v-bind:key="choice.id" v-for="choice in question.choices">
                    <b-container>
                        <b-row>
                            <b-col class="text-left" cols="8"><b-form-radio v-model="selected" :value="choice.text">{{choice.text}}</b-form-radio></b-col>
                            <b-col><b-button class="del-pos-btn" variant="outline-secondary" pill v-if="edit === true" @click="delPos(choice.id)">x</b-button></b-col>
                        </b-row>
                    </b-container>
                </div>
            </b-form-group>

            <div v-if="question.displayVariant === 'dropdown'">
                <b-form-select v-model="selected">
                    <b-form-select-option v-bind:key="choice.id" v-for="choice in question.choices" :value="choice.text" >{{choice.text}}</b-form-select-option>
                </b-form-select>

                <div v-if="edit">
                    <div v-bind:key="pos.id" v-for="pos in question.choices">
                        <b-container>
                            <b-row>
                                <b-col class="text-left" cols="8">{{pos.text}}</b-col>
                                <b-col><b-button class="del-pos-btn" variant="outline-secondary" pill v-if="edit === true" @click="delPos(pos.id)">x</b-button></b-col>
                            </b-row>
                        </b-container>
                    </div>
                </div>
            </div>
        </form>
    </QuestionCard>
</template>

<script>
    import TextQuestionModel from "../../../../store/poll-item-models/TextQuestionModel";
    import QuestionCard from "./QuestionCard";

    export default {
        name: "SingleChoiceQuestion",
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
