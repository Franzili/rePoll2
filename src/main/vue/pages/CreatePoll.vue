<template>
    <div>
        <nav-bar></nav-bar>
        <div class="my-head">
            <div class="my-titel">
                Title:
            </div>
            <div v-if="editTitle">
                <b-form-input  v-model="pollTitle"></b-form-input>
            </div>
            <div v-else>
                {{pollTitle}}
            </div>

            <div v-if="editTitle">
                <b-icon-check-all class="my-icon" scale="1.8" animation="fade" @click="changeEditTitle"></b-icon-check-all>
            </div>
            <div v-else>
                <b-icon-pencil class="my-icon" scale="1.2" @click="changeEditTitle"></b-icon-pencil>
            </div>
        </div>

        <!-- better for mobile version -->
        <div v-if="isMobile()">
            <div :key="question.id" v-for="question in questions">
                <PollQuestion v-bind:question="question" v-bind:edit="edit" v-on:del-question="deleteQuestion(question.id)"/>
            </div>
            <HelloWorld style="text-align:center;" class="ml-auto" msg=""/>
            <AddQuestion v-on:add-question="addQuestion"/>
        </div>

        <!-- better for Desktop version -->
        <!-- TODO dont drop the items back to the palette !!!-->
        <div v-else>
            <b-container>
                <b-row>
                    <b-col></b-col>
                    <b-col>
                        <div class="my-section">
                            <div v-if="editSection">
                                <b-form-input style="text-align: center" class="my-section-input" v-model="sectionTitle"></b-form-input>
                                <b-icon-check-all class="my-icon" scale="1.8" animation="fade" @click="changeEditSection"></b-icon-check-all>
                            </div>

                            <div v-else>
                                <p class="section">{{sectionTitle}}</p>
                                <b-icon-pencil class="my-icon" scale="1.2" @click="changeEditSection"></b-icon-pencil>
                            </div>
                        </div>
                    </b-col>
                    <b-col></b-col>
                </b-row>
            </b-container>



            <b-container>

                <!-- draggable palette items -->
                <b-row style="text-align:center;" class="my-row">
                    <b-col>
                        <div>
                            <b-button v-if="!visible" v-b-toggle.sidebar-1-footer @click="changePaletteVisible" >Add Question</b-button>
                            <b-sidebar v-model="visible" id="sidebar-1" bg-variant="gray-900" localShow=true>
                                <div class="px-3 py-2">

                                    <p class="margin-top-side">
                                        Add a Question into your Poll via Drag and Drop!
                                    </p>



                                    <div class="col-4">
                                        <draggable
                                            class="list-group" v-model="palette"
                                            group="group"
                                            @change="log"
                                            v-on:end="updatePollQuestionsText">

                                            <div class="drag-item flex flex-justify-between">

                                                <b-icon-pen></b-icon-pen>
                                                Text-Question

                                            </div>

                                        </draggable>
                                        <draggable
                                            class="list-group" v-model="palette"
                                            group="group"
                                            @change="log"
                                            v-on:end="updatePollQuestionsLimChar">

                                            <div class="drag-item flex flex-justify-between">

                                                <b-icon-pencil-square></b-icon-pencil-square>
                                                Limited Character Question

                                            </div>

                                        </draggable>

                                        <draggable
                                            class="list-group" v-model="palette"
                                            group="group"
                                            @change="log"
                                            v-on:end="updatePollQuestionsPossibilities">

                                            <div class="drag-item flex flex-justify-between">

                                                <b-icon-list-task></b-icon-list-task>
                                                Single Choice Question

                                            </div>

                                        </draggable>

                                        <draggable
                                            class="list-group" v-model="palette"
                                            group="group"
                                            @change="log"
                                            v-on:end="updatePollQuestionsCheckbox">

                                            <div class="drag-item flex flex-justify-between">

                                                <b-icon-list-task></b-icon-list-task>
                                                Multiple Choice Question

                                            </div>

                                        </draggable>

                                        <draggable
                                            class="list-group" v-model="palette"
                                            group="group"
                                            @change="log"
                                            v-on:end="updatePollQuestionsSelect">

                                            <div class="drag-item flex flex-justify-between">

                                                <b-icon-caret-down-fill></b-icon-caret-down-fill>
                                                Select Answer Question

                                            </div>
                                        </draggable>

                                        <draggable
                                            class="list-group" v-model="palette"
                                            group="group"
                                            @change="log"
                                            v-on:end="updatePollQuestionsX">

                                            <div class="drag-item flex flex-justify-between">

                                                <b-icon-question-square></b-icon-question-square>
                                                Slider Question

                                            </div>
                                        </draggable>



                                    </div>
                                </div>
                            </b-sidebar>
                        </div>
                    </b-col>
                    <b-col class="cols-14">
                            <div>
                                <draggable
                                    class="list-group" v-model="questions"
                                    group="group"
                                    @change="log">
                                    <div class="drag-item flex flex-justify-between" :key="question.id" v-for="question in questions">
                                        <PollQuestion v-bind:question="question" v-bind:edit="edit" v-on:del-question="deleteQuestion(question.id)"/>
                                    </div>
                                </draggable>
                                <b-button @click="updatePoll">save</b-button>
                            </div>
                        <HelloWorld class="ml-auto" msg=""/>
                    </b-col>
                    <b-col></b-col>
                </b-row>
            </b-container>
        </div>
    </div>
</template>

<script>

    import draggable from "vuedraggable"
    import HelloWorld from '../components/HelloWorld.vue'
    import NavBar from "../components/NavBar";
    import AddQuestion from "../components/AddQuestion";
    import PollQuestion from "../components/PollQuestion";
    import {v4 as uuidv4} from "uuid";
    import axios from 'axios';
    import {mapActions, mapGetters} from "vuex";

    export default {
        name: "CreatePoll",
        data() {
            return {
                visible:true,
                tmpID: 0,
                id: "c4fdc95e-11e7-46ef-9396-83c950e0d482",
                title: "Moby Dick",
                status: "IN_PROCESS",
                sections: [],
                pollTitle: "Moby Dick",
                sectionTitle: "1. The Society",
                editTitle: false,
                editSection: false,
                edit: true,
                questions: [],
                palette: []
            }
        },
        created: function () {
            this.tmpID = this.$route.params.tmpPollID;
            this.requestPolls()
            this.requestPoll(this.tmpID)
        },
        computed: {
            ...mapGetters(['getPoll']),
            survey() {
                return this.getPoll(this.tmpID)
            }
        },
        methods: {
            changePaletteVisible(){
                this.visible = !this.visible;
            },
            changeEditTitle() {
                this.editTitle = !this.editTitle;
            },
            changeEditSection() {
                this.editSection = !this.editSection;
            },
            updatePoll() {
                // update poll
                let pollCmd = {
                    title: this.title,
                    status: this.status
                };
                axios.put('/api/v1/polls/'+ this.id + '/', pollCmd);

                // update questions
                for (var i = 0; i < this.questions.length; i++) {
                    axios.put(
                        '/api/v1/polls/' + this.id + '/questions/' + this.questions[i].id + '/',
                        this.questions[i]                    )
                }

                // update sections
                for (i = 0; i < this.sections.length; i++) {
                    axios.put(
                        '/api/v1/polls/' + this.id + '/sections/' + this.sections[i].id + '/',
                        this.sections[i]
                    )
                }
            },
            deleteQuestion(id) {
                this.questions = this.questions.filter(question => question.id !== id);
            },
            addQuestion(newQuestion) {
                this.questions = [...this.questions, newQuestion];
            },
            isMobile() {
                return (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent));
            },
            updatePollQuestionsPossibilities() {
                const newQuestion = {
                    id: uuidv4(),
                    type: "radio",
                    title: "new question",
                    possibilities: [
                        {
                            id: 1,
                            text: "choice 1",
                        },
                        {
                            id: 2,
                            text: "choice 2",
                        },
                        {
                            id: 3,
                            text: "choice 3",
                        }
                    ]
                };
                this.questions = [...this.questions, newQuestion];
            },
            updatePollQuestionsSelect() {
                const newQuestion = {
                    id: uuidv4(),
                    type: "dropdown",
                    title: "Was ist deine Lieblingsfarbe?",
                    possibilities: [
                        {
                            id: 1,
                            text: "choice 1",
                        },
                        {
                            id: 2,
                            text: "choice 2",
                        },
                        {
                            id: 3,
                            text: "choice 3",
                        }
                    ]
                };
                this.questions = [...this.questions, newQuestion];
            },
            updatePollQuestionsText() {
                const newQuestion = {
                    id: uuidv4(),
                    type: "section",
                    title: "new question",
                };
                this.questions = [...this.questions, newQuestion];
            },
            updatePollQuestionsLimChar() {
                const newQuestion = {
                    id: uuidv4(),
                    type: "freetext",
                    title: "new question",
                    possibilities: [
                        {
                            limit: 10
                        }
                    ]
                };
                this.questions = [...this.questions, newQuestion];
            },
            updatePollQuestionsX() {
                const newQuestion = {
                    id: uuidv4(),
                    type: "slider",
                    title: "new question",
                    possibilities: [
                        {
                            min: 1,
                            max: 10,
                            step: 1
                        }
                    ]
                };
                this.questions = [...this.questions, newQuestion];
            },
            updatePollQuestionsCheckbox() {
                const newQuestion = {
                    id: uuidv4(),
                    type: "checkbox",
                    title: "new question",
                    possibilities: [
                        {
                            id: 1,
                            text: "choice 1",
                        },
                        {
                            id: 2,
                            text: "choice 2",
                        },
                        {
                            id: 3,
                            text: "choice 3",
                        }
                    ]
                };
                this.questions = [...this.questions, newQuestion];
            },
            log: function (...e) {
                    console.log(...e);
            },
            ...mapActions(['requestPoll','requestPolls'])
        },
        components: {
            AddQuestion,
            PollQuestion,
            NavBar,
            HelloWorld,
            draggable
        }
    }
</script>

<style scoped>

    .my-row {
        padding: 20px;
    }

    .drag-item {
        padding: 15px 10px;
        background-color: whitesmoke;
        min-width: 10vw;
        max-width: 90vw;
        margin: 0 auto 10px;
        cursor: grab;
        transition: transform 0.25s cubic-bezier(0.02, 1.01, 0.94, 1.01);
        will-change: transform;
    }

    .my-titel {
        color: #868686;
        margin-right: 5px;
    }

    .my-icon {
        margin-left: 20px;
    }

    .my-head {
        text-align:center;
        background-color: lightgray;
        font-size: 25px;
        padding: 1px;
    }

    .my-head > div {
        vertical-align:top;
        display:inline-block;
    }

    .section {
        margin-top: 20px;
        margin-bottom: 0px;
        color: #868686;
        font-size: 30px;
        text-align: center;
    }

    .my-section {
        text-align:center;
        font-size: 30px;
        padding: 1px;
    }

    .margin-top-side {
        margin-top: 70px;
    }

    .my-section-input {
        margin-top: 20px;
        margin-bottom: 10px;
    }
</style>
