<template>
    <div>
        <p>Dies ist die Surveyseite für die Survey mit der ID {{ id }}</p>
    </div>

</template>

<script>

    import {mapGetters} from "vuex";
    import NavBar from "../components/NavBar";
    import HelloWorld from "../components/HelloWorld";
    import SurveyItemList from "../components/SurveyItemList";

    export default {
        name: "Survey",
        data() {
            return {
                id: 0
            }
        },
        created() {
            this.id = this.$route.params.id
        },
        computed: {
            ...mapGetters(['getSurvey']),
            survey() {
                return this.getSurvey(this.id)
            }
        },
        comments: {
            NavBar,
            HelloWorld,
            SurveyItemList
        },
    }
</script>

<style scoped>

    .my-row {
        padding: 20px;
    }
</style>
