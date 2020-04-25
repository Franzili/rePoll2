<template>
    <div :style="{ backgroundColor:'lightblue'}">
        <nav-bar></nav-bar>
        <HelloWorld align="center" class="ml-auto" msg="Create your own Survey!"/>

        <!-- better for mobile version -->
        <div v-if="isMobile()">
            <SurveyItemList v-bind:items="items" v-on:del-item="deleteItem"/>
            <HelloWorld align="center" class="ml-auto" msg=""/>
            <AddQuestion v-on:add-item="addItem"/>
        </div>

        <!-- better for Desktop version -->
        <div v-else>
            <b-container class="my-contaier">

                <b-row align="center" class="my-row" cols="3">
                    <b-col>Edit Area</b-col>
                    <b-col>Surveyname</b-col>
                    <b-col>Edit elements:</b-col>
                </b-row>

                <b-row align="center" class="my-row">
                    <b-col></b-col>
                    <b-col>
                        <SurveyItemList v-bind:items="items" v-on:del-item="deleteItem"/>
                        <HelloWorld align="center" class="ml-auto" msg=""/>
                        <AddQuestion v-on:add-item="addItem"/>
                    </b-col>
                    <b-col>
                    </b-col>
                </b-row>

            </b-container>
        </div>

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
    import AddQuestion from "../components/AddQuestion";

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
        methods: {
            deleteItem(id) {
                this.items = this.items.filter(item => item.id !== id);
            },
            addItem(newItem) {
                this.items = [...this.items, newItem];
            },
            isMobile() {
                if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)) {
                    return true
                } else {
                    return false
                }
            }
        },
        components: {
            AddQuestion,
            SurveyItemList,
            NavBar,
            HelloWorld
        }
    }
</script>

<style scoped>

    .my-row {
        padding: 20px;
    }
</style>
