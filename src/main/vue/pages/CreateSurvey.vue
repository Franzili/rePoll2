<template>
    <div :style="{ backgroundColor:'lightblue'}">
        <nav-bar></nav-bar>
        <HelloWorld align="center" class="ml-auto" msg="Create your own Survey!"/>

        <b-container class="my-contaier">

            <b-row align="center" class="my-row" cols="3">
                <b-col>Edit Area</b-col>
                <b-col>Beschreibung der Umfrage</b-col>
                <b-col>Edit elements:</b-col>
            </b-row>

            <b-row align="center" class="my-row">
                <b-col></b-col>
                <b-col>
                    <SurveyItemList v-bind:items="items"/>
                </b-col>
                <b-col>
                    <b-button type="submit" variant="secondary">edit</b-button>
                    <b-button type="submit" variant="danger">delete</b-button>
                </b-col>
            </b-row>

            <b-row align="center" class="my-row">
                <b-col></b-col>
                <b-col>Here Should be a Slider</b-col>
                <b-col>
                    <b-button type="submit" variant="secondary">edit</b-button>
                    <b-button type="submit" variant="danger">delete</b-button>
                </b-col>
            </b-row>

        </b-container>

        <!-- to fill the whole page for background -->
        <HelloWorld align="center" class="ml-auto" msg=""/>
        <HelloWorld align="center" class="ml-auto" msg=""/>
        <HelloWorld align="center" class="ml-auto" msg=""/>
        <HelloWorld align="center" class="ml-auto" msg=""/>
        <HelloWorld align="center" class="ml-auto" msg=""/>
        <HelloWorld align="center" class="ml-auto" msg=""/>
        <HelloWorld align="center" class="ml-auto" msg=""/>
        <HelloWorld align="center" class="ml-auto" msg=""/>
    </div>
</template>

<script>

    import HelloWorld from '../components/HelloWorld.vue'
    import NavBar from "../components/NavBar";
    import SurveyItemList from "../components/SurveyItemList";

    export default {
        name: "CreateSurvey",
        data() {
            return {
                backendData: "",
                form: {
                    email: '',
                    name: '',
                    checked: []
                },
                show: true,
                items: [
                    {
                        id: 1,
                        question: "how are you"
                    },
                    {
                        id: 2,
                        question: "wie gehts"
                    }
                ]
            }
        },
        components: {
            SurveyItemList,
            NavBar,
            HelloWorld
        },
    }
</script>

<style scoped>

    .my-row {
        padding: 20px;
    }
</style>
