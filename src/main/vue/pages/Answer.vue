<template>
    <div style="text-align:center;">
    <b-container class="mt-3">
        <nav-bar></nav-bar>
        <HelloWorld></HelloWorld>
        <b-row>
            <b-col>
                    <b-form @submit.prevent="save">
                        <b-form-group>
                            <p class="my-name">{{survey.title}}</p>
                        </b-form-group>
                        <b-row>
                            <b-col></b-col>
                            <b-form-group>

                                <div :key="item.id" v-for="item in this.survey.questions">

                                <!--
                                <div :key="item.id" v-for="item in items">
                                    -->
                                    <SurveyItem v-bind:item="item" v-bind:edit="edit"/>
                                </div>
                            </b-form-group>
                            <b-col></b-col>
                        </b-row>
                        <!--
                        template save buttons!!!
                        <b-button variant="primary" type="submit">Speichern</b-button>
                        -->
                        <b-button @click="save">Save</b-button>
                        <!--
                        Submit Button for later
                        Final Submit, then answers can't be edited animore
                        <b-button class="my-button" variant="success">Submit!</b-button>
                        -->
                    </b-form>
            </b-col>
        </b-row>
    </b-container>
    </div>
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
                items: [],
            }
        },
        created() {
            this.id = this.$route.params.id
            this.survey = this.getSurvey(this.id)
            //this.setQuestion()
        },
        computed: {
            ...mapGetters(['getSurvey'])

    },
    methods: {
        isMobile() {
            return (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent));
        },
        ...mapMutations([
            'updateSurvey'
        ]),
        save() {
            this.updateSurvey(this.survey)
            this.$router.push('/') //redirect to page '', here start page
        },
        //TODO go through whole array to change title to question
        /*setQuestion() {
            for (var i = 0; i < this.survey.questions.length; i++) {
                const newItem = {
                    id: this.survey.questions[i].id,
                    question: this.survey.questions[i].title,
                    type: 'freetext' //TODO for all types,now missing in backend
                }
                this.items = [...this.items, newItem]
                //this.items.push({newItem});
            }
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
</style>
