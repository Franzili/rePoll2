<template>
    <div align="center">
        <p>{{item.question}}</p>
        <b-form-group id="input-group-1">
            <b-form-checkbox-group id="checkboxes-4">
                <b-form-checkbox value="c1">very good</b-form-checkbox>
                <b-form-checkbox value="c2">good</b-form-checkbox>
                <b-form-checkbox value="c3">not good</b-form-checkbox>
                <b-form-checkbox value="c4">bad</b-form-checkbox>
            </b-form-checkbox-group>
        </b-form-group>
        <b-button @click="$emit('del-item', item.id)" variant="danger">delete</b-button>
        <p class="bottom-line"></p>
    </div>
</template>

<script>
    export default {
        name: "SurveyItem",
        props: ["item"]
    }
</script>

<style scoped>

    .bottom-line {
        padding: 10px;
        border-bottom: 1px #000000 dotted;
    }
</style>
