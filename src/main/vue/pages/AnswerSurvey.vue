<template>
    <div style="text-align:center;">
        <nav-bar></nav-bar>
        <HelloWorld class="ml-auto" msg="Answer your Survey!"/>

        <b-container>
            <b-row>
                <b-col></b-col>
                <b-col>
                    <div :key="item.id" v-for="item in items">
                        <SurveyItem v-bind:item="item" v-bind:edit="edit"/>
                    </div>
                </b-col>
                <b-col></b-col>
            </b-row>
        </b-container>

        <b-button class="my-button" variant="primary">Save</b-button>
        <b-button class="my-button" variant="success">Submit!</b-button>
    </div>
</template>

<script>
    import NavBar from "../components/NavBar";
    import HelloWorld from "../components/HelloWorld";
    import SurveyItem from "../components/SurveyItem";

    export default {
        name: "AnswerSurvey",
        data() {
            return {
                edit: false,
                items: [
                    {
                        id: 1,
                        type: "freetext",
                        question: "wie gehts",
                        possibilities: []
                    },
                    {
                        id: 2,
                        type: "checkbox",
                        question: "how are you",
                        possibilities: [
                            {
                                id: 1,
                                text: "bla bla asdikawzug l hif",
                            },
                            {
                                id: 2,
                                text: "yes",
                            }
                        ]
                    },
                    {
                        id: 3,
                        type: "radio",
                        question: "wie gehts",
                        possibilities: [
                            {
                                id: 1,
                                text: "bla bla asdikawzug l hif",
                            },
                            {
                                id: 2,
                                text: "yes",
                            }
                        ]
                    },
                    {
                        id: 4,
                        type: "dropdown",
                        question: "wie gehts",
                        possibilities: [
                            {
                                id: 1,
                                text: "bla bla asdikawzug l hif",
                            },
                            {
                                id: 2,
                                text: "yes",
                            }
                        ]
                    }
                ]
            }
        },
        components: {
            NavBar,
            HelloWorld,
            SurveyItem
        },
    }
</script>

<style scoped>
    .my-button{
        margin-left: 10px;
        margin-right: 10px;
    }

</style>
