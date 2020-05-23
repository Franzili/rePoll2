<template>
    <div>
        <p>Dies ist die Surveyseite für die Survey mit der ID {{ id }}</p>
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
            ...mapGetters(['getSurvey']),
            survey() {
                return this.getSurvey(this.id)
            }
        },
        methods: {
            ...mapActions(['requestSurvey'])
        },
        comments: {
            NavBar,
            HelloWorld
            //,
            //SurveyItemList
        },
    }
</script>

<style scoped>

    .my-row {
        padding: 20px;
    }
</style>
