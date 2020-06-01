<template>
    <b-card class="question-card">
        <b-card-title>{{ title }}</b-card-title>
        <slot></slot>
    </b-card>
</template>

<script>
    export default {
        name: "QuestionCard",
        props: {
            title: {
                type: String,
                required: true
            }
        }
    }
</script>

<style lang="scss" scoped>
    @import '~bootstrap/scss/bootstrap.scss';
    .question-card {
        margin-bottom: 25px;
    }
</style>
