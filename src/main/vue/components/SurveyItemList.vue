<template>
    <div>
        <div v-bind:key="item.id" v-for="item in items">
            <SurveyItem v-bind:item="item" v-bind:edit="edit" v-on:del-item="$emit('del-item', item.id)"/>
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
        props: ["items", "edit"]
    }
</script>

<style scoped>

</style>
