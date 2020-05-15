<!--
surveyTabelElement als template testen
-->
<template>
    <div style="text-align:center;">
        <nav-bar></nav-bar>
        <HelloWorld class="ml-auto" msg="Answer your Survey!"/>



        <div>
            Titel: <input type="text" v-model="survey.name">
        </div>
        <!--
        items , also fragebn hier statt test
        <SurveyTableList v-bind:surveys="surveys"/>

         <SurveyItem v-bind:item="item" v-bind:edit="edit"/>
        -->

        <div :key="item.id" v-for="item in items">
            <SurveyItem v-bind:item="item" v-bind:edit="edit"/>
        </div>

        <!--
        <div>
            <SurveyItem v-bind:items="items" v-bind:edit="edit"/>
        </div>
        -->


        <b-button class="my-button" variant="primary">Save</b-button>
        <b-button class="my-button" variant="success">Submit!</b-button>

    </div>
</template>


<script>
    import NavBar from "../components/NavBar";
    import HelloWorld from "../components/HelloWorld";
    import {mapGetters} from "vuex";
    import SurveyItem from "../components/SurveyItem"; //mapMutations

    export default {
        name: "Answer",
        components: {SurveyItem, HelloWorld, NavBar},
        data() {
            return {
                id: 0,
                article: {}
            }
        },
        created() {
            this.id = this.$route.params.id
            this.survey = this.getSurvey(this.id)
            this.item = this.getItem(this.id)
        },
        computed: {
            ...mapGetters(['getSurvey']),
            ...mapGetters(['getItem'])

    },


    methods: {
        isMobile() {
            return (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent));
        }
        /*...mapMutations([
            'updateSurvey'
        ]),
        answer() {
            this.updateSurvey(this.survey)
            this.$router.push('/')
        }*/
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
    .my-button{
        margin-left: 10px;
        margin-right: 10px;
    }
</style>
