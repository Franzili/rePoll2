<template>
    <div style="text-align:center;">
        <nav-bar></nav-bar>
        <HelloWorld class="ml-auto" msg="Fill out your Survey!"/>



        <div>
            Titel: <input type="text" v-model="survey.title">
        </div>
        <div>
            Text: <textarea v-model="survey.text" rows="7"></textarea>


        </div>
        <b-button class="my-button" variant="primary">Save</b-button>
        <b-button class="my-button" variant="success">Submit!</b-button>

    </div>
</template>

<script>
    import NavBar from "../components/NavBar";
    import HelloWorld from "../components/HelloWorld";
    import SurveyItemList from "../components/SurveyItemList";

    import {mapGetters} from "vuex"; //mapMutations

    export default {
        name: "Answer",
        data() {
            return {
                id: 0,
                article: {}
            }
        },
        created() {
            this.id = this.$route.params.id
            this.survey = this.getSurvey(this.id)
        },
        computed: {
            ...mapGetters(['getSurvey']),
        },
        /*methods: {
            ...mapMutations([
                'updateSurvey'
            ]),
            answer() {
                this.updateSurvey(this.survey)
                this.$router.push('/')
            }
        },*/
        comments: {
            NavBar,
            HelloWorld,
            SurveyItemList
        },
    }

</script>

<style scoped>
    .my-button{
        margin-left: 10px;
        margin-right: 10px;
    }
</style>
