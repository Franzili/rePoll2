<template>
    <div>
        <div v-bind:key="item.id" v-for="item in items">
            <SurveyItem v-bind:item="item"/>
        </div>
    </div>
</template>

<script>
    import SurveyItem from "./SurveyItem";

    export default {
        name: "SurveyItemList",
        components: {
            SurveyItem
        },
        props: ["items"]
    }
</script>

<style scoped>

</style>
