<template>
    <b-card class="question-card">
        <b-card-title>{{ title }}</b-card-title>
        <slot></slot>
        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. A accusantium alias atque dolorum ducimus eum fuga
            fugit id laborum molestiae molestias, necessitatibus quisquam quod reiciendis rerum, sapiente sequi suscipit
            voluptate!</p>
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
