<!--
surveyTabelElement als template testen
-->
<!--
<template>
    <div style="text-align:center;">
        <nav-bar></nav-bar>
        <HelloWorld class="ml-auto" msg="Answer your Survey!"/>



        <div>
            Titel: <input type="text" v-model="survey.name">
        </div>
        <!-
        items , also fragebn hier statt test
        <SurveyTableList v-bind:surveys="surveys"/>

         <SurveyItem v-bind:item="item" v-bind:edit="edit"/>
        ->

        <div :key="item.id" v-for="item in items">
            <SurveyItem v-bind:item="item" v-bind:edit="false"/>
        </div>

        <!-
        <div>
            <SurveyItem v-bind:items="items" v-bind:edit="edit"/>
        </div>
        ->


        <b-button class="my-button" variant="primary">Save</b-button>
        <b-button class="my-button" variant="success">Submit!</b-button>

    </div>
</template>
-->

<template>
    <b-container class="mt-3">
        <nav-bar></nav-bar>
        <HelloWorld></HelloWorld>
        <b-row>
            <b-col>
                <!--
                <b-card header="Fill out your survey">
                    -->
                    <b-form @submit.prevent="save">
                        <b-form-group>
                            <!--
                            <b-form-input type="text" v-model="survey.name"></b-form-input>
                            -->
                            <p class="my-name">{{survey.name}}</p>
                        </b-form-group>
                        <b-form-group>
                            <!--
                            <b-form-textarea type="textarea" rows="10" v-model="survey.item"></b-form-textarea>
                            -->
                            <div :key="item.id" v-for="item in survey.items">
                                <SurveyItem v-bind:item="item" v-bind:edit="edit"/>
                            </div>
                        </b-form-group>
                        <!--
                        template save buttons!!!
                        <b-button variant="primary" type="submit">Speichern</b-button>
                        -->
                        <b-button class="my-button" variant="primary">Save</b-button>
                        <b-button class="my-button" variant="success">Submit!</b-button>
                    </b-form>
                <!--
                </b-card>
                -->
            </b-col>
        </b-row>
    </b-container>
</template>


<script>
    import NavBar from "../components/NavBar";
    import HelloWorld from "../components/HelloWorld";
    import {mapGetters, mapMutations} from "vuex";
    import SurveyItem from "../components/SurveyItem"; //mapMutations

    export default {
        name: "Answer",
        components: {SurveyItem, HelloWorld, NavBar},
        data() {
            return {
                id: 0,
                survey: {}
                /*
                ,
                items: []
                */
            }
        },
        mounted() {
            this.id = this.$route.params.id
            this.survey = this.getSurvey(this.id)
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
        },
        ...mapMutations([
            'updateSurvey'
        ]),
        answer() {
            this.updateSurvey(this.survey)
            this.$router.push('/')
        }
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
