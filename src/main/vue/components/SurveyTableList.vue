<template>
    <div>
        <div v-bind:key="survey.id" v-for="survey in surveys">
            <SurveyListElement></SurveyListElement>
        </div>
    </div>
</template>

<script>
    import SurveyListElement from "./SurveyTableElement";

    export default {
        name: "SurveyTableList",
        components: {
            SurveyListElement
        },
        props: ["surveys"]
    }
</script>

<style scoped>

</style>
