<template>
    <b-row>
        <p>{{ model.title }}</p>
    </b-row>
</template>

<script>
    import TextQuestionModel from "../../../../store/poll-item-models/TextQuestionModel";

    export default {
        name: "TextQuestion",
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
