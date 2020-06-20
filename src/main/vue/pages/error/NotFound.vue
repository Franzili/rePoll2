<template>
    <b-container>
        <b-jumbotron header="404 - Not found."
                     :lead="'I don\'t know  what you\'re looking for.'"/>
    </b-container>
</template>

<script>
    export default {
        name: "Forbidden.vue",
    }
</script>

<style lang="scss" scoped>
    @import '~bootstrap/scss/bootstrap.scss';
    .jumbotron {
        background-color: white;
        border: 5px solid $border-color;
    }
</style>
