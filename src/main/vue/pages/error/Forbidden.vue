<template>
    <b-container>
        <b-jumbotron header="403 - Forbidden"
                     :lead="'I\'m afraid I can\'t let you do that, ' + username + '.'"/>
    </b-container>
</template>

<script>
    import {mapState} from "vuex";

    export default {
        name: "Forbidden.vue",
        computed: {
            ...mapState('auth', {
                username: 'username'
            })
        }
    }
</script>

<style lang="scss" scoped>
    @import '~bootstrap/scss/bootstrap.scss';
    .jumbotron {
        background-color: white;
        border: 5px solid $border-color;
    }
</style>
