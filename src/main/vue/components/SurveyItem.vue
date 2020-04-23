<template>
    <div>
        <p>{{item.question}}</p>
        <b-form-group id="input-group-1">
            <b-form-checkbox-group id="checkboxes-4">
                <b-form-checkbox value="c1">very good</b-form-checkbox>
                <b-form-checkbox value="c2">good</b-form-checkbox>
                <b-form-checkbox value="c3">not good</b-form-checkbox>
                <b-form-checkbox value="c4">bad</b-form-checkbox>
            </b-form-checkbox-group>
        </b-form-group>
    </div>
</template>

<script>
    export default {
        name: "SurveyItem",
        props: ["item"]
    }
</script>

<style scoped>

</style>
