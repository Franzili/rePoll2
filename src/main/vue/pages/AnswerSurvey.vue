<template>
    <div style="text-align:center;">
        <nav-bar></nav-bar>
        <HelloWorld class="ml-auto" msg="Answer your Poll!"/>

        <div v-if="isMobile()">
            <div :key="item.id" v-for="item in items">
                <SurveyItem v-bind:item="item" v-bind:edit="edit"/>
            </div>
        </div>

        <div v-else>
            <b-container>
                <b-row>
                    <b-col></b-col>
                    <b-col cols="5">
                        <div :key="item.id" v-for="item in items">
                            <SurveyItem v-bind:item="item" v-bind:edit="edit"/>
                        </div>
                    </b-col>
                    <b-col></b-col>
                </b-row>
            </b-container>
        </div>

        <hr>

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
                        type: "radio",
                        question: "Wie findest du Repoll?",
                        possibilities: [
                            {
                                id: 1,
                                text: "perfekt",
                            },
                            {
                                id: 2,
                                text: "sehr gut",
                            },
                            {
                                id: 3,
                                text: "gut",
                            }
                        ]
                    },
                    {
                        id: 3,
                        type: "section",
                        question: "Was ist deine Lieblingsprogrammiersprache?",
                        possibilities: [
                            {
                                limit: 10
                            }
                        ]
                    },
                    {
                        id: 4,
                        type: "slider",
                        question: "Wie gerne magst du Brokkoli, von 1 bis 10?",
                        possibilities: [
                            {
                                min: 1,
                                max: 10,
                                step: 1
                            }
                        ]
                    },
                    {
                        id: 5,
                        type: "checkbox",
                        question: "Was hast du für Gewohnheiten?",
                        possibilities: [
                            {
                                id: 1,
                                text: "Meditieren",
                            },
                            {
                                id: 2,
                                text: "Sport",
                            },
                            {
                                id: 3,
                                text: "Programmieren",
                            }
                        ]
                    }
                ]
            }
        },
        methods: {
            isMobile() {
                return (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent));
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
