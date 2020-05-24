<template>
    <div>
        <p>Dies ist die Pollseite für den Poll mit der ID {{ id }}</p>
    </div>

</template>

<script>

    import {mapActions, mapGetters} from "vuex";
    import NavBar from "../components/NavBar";
    import HelloWorld from "../components/HelloWorld";

    export default {
        name: "Poll",
        data() {
            return {
                id: 0
            }
        },
        created() {
            this.id = this.$route.params.id
            this.requestPoll(this.id)
        },
        computed: {
            ...mapGetters(['getPoll']),
            Poll() {
                return this.getPoll(this.id)
            }
        },
        methods: {
            ...mapActions(['requestPoll'])
        },
        comments: {
            NavBar,
            HelloWorld
        },
    }
</script>

<style scoped>
</style>
