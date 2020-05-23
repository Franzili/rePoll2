<template>
    <div>
        <div v-bind:key="survey.id" v-for="survey in surveys">
            <AnswerSurveyListElement v-bind:survey="survey"/>
        </div>
    </div>
</template>

<script>
    import AnswerSurveyListElement from "./AnswerSurveyTableElement";

    export default {
        name: "SurveyTableList",
        components: {
            AnswerSurveyListElement
        },
        props: ["surveys"]
    }
</script>

<style scoped>

</style>
