<template>
    <QuestionCard v-bind:title="model.title">
        BLABLABLABLA
    </QuestionCard>
</template>

<script>
    import TextQuestionModel from "../../../../store/poll-item-models/TextQuestionModel";
    import QuestionCard from "./QuestionCard";

    export default {
        name: "TextQuestion",
        components: {QuestionCard},
        props: {
            model: {
                type: TextQuestionModel,
                required: true
            }
        }

    }
</script>

<style scoped>

</style>
