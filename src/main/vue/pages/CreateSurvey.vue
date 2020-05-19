<template>
    <div>
        <nav-bar></nav-bar>
        <div class="my-head">
            <div class="my-titel">
                Titel:
            </div>
            <div v-if="editTitle">
                <b-form-input  v-model="surveyTitle"></b-form-input>
            </div>
            <div v-else>
                {{surveyTitle}}
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
            <div :key="item.id" v-for="item in items">
                <SurveyItem v-bind:item="item" v-bind:edit="edit" v-on:del-item="deleteItem(item.id)"/>
            </div>
            <HelloWorld style="text-align:center;" class="ml-auto" msg=""/>
            <AddQuestion v-on:add-item="addItem"/>
        </div>

        <!-- better for Desktop version -->
        <div v-else>
            <p class="section">2. Die Gesellschaft</p>
            <b-container>
                <b-row style="text-align: center">
                    <b-col>Palette</b-col>
                    <b-col>Umfrage</b-col>
                    <b-col>Gliederung</b-col>
                </b-row>

                <!-- draggable palette items -->
                <b-row style="text-align:center;" class="my-row">
                    <b-col>
                        <div>
                            <b-button v-if="!visible" v-b-toggle.sidebar-1-footer @click="changePaletteVisible" >Add Question</b-button>
                            <b-sidebar v-model="visible" id="sidebar-1" bg-variant="light" localShow=true title="Add Questions" shadow>
                                <div class="px-3 py-2">

                                    <p>
                                        Add a Question into your Survey via Drag and Drop!
                                    </p>
                                    <div class="col-4">
                                        <draggable

                                            class="list-group" v-model="palette"

                                            group="group"

                                            @change="log"

                                            v-on:end="updateSurveyItemsText"

                                        >

                                            <div class="drag-item flex flex-justify-between">

                                                <!-- <b-form-input v-model="items[0].question"></b-form-input> -->

                                                <b-icon-pen></b-icon-pen>

                                                <b-text> Text-Question</b-text>

                                                <!-- <AddQuestion v-on:add-item="addItem"/> -->

                                            </div>

                                        </draggable>
                                        <draggable

                                            class="list-group" v-model="palette"

                                            group="group"

                                            @change="log"

                                            v-on:end="updateSurveyItemsLimChar"

                                        >

                                            <div class="drag-item flex flex-justify-between">

                                                <!-- <b-form-input v-model="items[0].question"></b-form-input> -->

                                                <b-icon-pencil-square></b-icon-pencil-square>

                                                <b-text> Limited Character Question</b-text>

                                                <!-- <AddQuestion v-on:add-item="addItem"/> -->

                                            </div>

                                        </draggable>

                                        <draggable

                                            class="list-group" v-model="palette"

                                            group="group"

                                            @change="log"

                                            v-on:end="updateSurveyItemsPossibilities"

                                        >

                                            <div class="drag-item flex flex-justify-between">

                                                <!-- <b-form-input v-model="items[0].question"></b-form-input> -->

                                                <b-icon-list-task></b-icon-list-task>

                                                <b-text> Single Choice Question</b-text>

                                                <!-- <AddQuestion v-on:add-item="addItem"/> -->

                                            </div>

                                        </draggable>

                                        <draggable

                                            class="list-group" v-model="palette"

                                            group="group"

                                            @change="log"

                                            v-on:end="updateSurveyItemsCheckbox"

                                        >

                                            <div class="drag-item flex flex-justify-between">

                                                <!-- <b-form-input v-model="items[0].question"></b-form-input> -->

                                                <b-icon-list-task></b-icon-list-task>

                                                <b-text> Multiple Choice Question </b-text>

                                                <!-- <AddQuestion v-on:add-item="addItem"/> -->

                                            </div>

                                        </draggable>

                                        <draggable

                                            class="list-group" v-model="palette"

                                            group="group"

                                            @change="log"

                                            v-on:end="updateSurveyItemsSelect"

                                        >

                                            <div class="drag-item flex flex-justify-between">

                                                <!-- <b-form-input v-model="items[0].question"></b-form-input> -->

                                                <b-icon-caret-down-fill></b-icon-caret-down-fill>

                                                <b-text> Select Answer Question</b-text>

                                                <!-- <AddQuestion v-on:add-item="addItem"/> -->

                                            </div>

                                        </draggable>

                                        <draggable

                                            class="list-group" v-model="palette"

                                            group="group"

                                            @change="log"

                                            v-on:end="updateSurveyItemsX"

                                        >

                                            <div class="drag-item flex flex-justify-between">

                                                <!-- <b-form-input v-model="items[0].question"></b-form-input> -->

                                                <b-icon-question-square></b-icon-question-square>

                                                <b-text> Slider Question</b-text>

                                                <!-- <AddQuestion v-on:add-item="addItem"/> -->

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
                                    class="list-group" v-model="items"
                                    group="group"
                                    @change="log">
                                    <div class="drag-item flex flex-justify-between" :key="item.id" v-for="item in items">
                                        <SurveyItem v-bind:item="item" v-bind:edit="edit" v-on:del-item="deleteItem(item.id)"/>
                                    </div>
                                </draggable>
                                <b-button @click="updatePoll">save</b-button>
                            </div>
                        <HelloWorld class="ml-auto" msg=""/>
                    </b-col>
                    <b-col>
                    </b-col>
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
    import SurveyItem from "../components/SurveyItem";
    import {v4 as uuidv4} from "uuid";
    import axios from 'axios';

    export default {
        name: "CreateSurvey",
        data() {
            return {
                visible:true,
                id: "c4fdc95e-11e7-46ef-9396-83c950e0d482",
                title: "Moby Dick",
                status: "IN_PROCESSING",
                sections: [],
                surveyTitle: "Moby Dick",
                editTitle: false,
                edit: true,
                items: [{
                    id: uuidv4(),
                    type: "radio",
                    question: "Testquestion with Possibility",
                    possibilities: [
                        {
                            id: 1,
                            text: "Wie geht es dir Morgens?",
                        },
                        {
                            id: 2,
                            text: "Wie geht es dir Mittags?",
                        },
                        {
                            id: 3,
                            text: "Wie geht es dir Abends?",
                        }
                    ]
                }
                ],
                palette: []
            }
        },
        methods: {
            changePaletteVisible(){
                this.visible = !this.visible;
            },
            changeEditTitle() {
                this.editTitle = !this.editTitle;
            },
            updatePoll() {
                // update poll
                let pollCmd = {
                    title: this.title,
                    status: this.status
                };
                axios.put('/api/v1/polls/'+ this.id + '/', pollCmd);

                // update questions
                for (var i = 0; i < this.items.length; i++) {
                    axios.put(
                        '/api/v1/polls/' + this.id + '/questions/' + this.items[i].id + '/',
                        this.items[i]                    )
                }

                // update sections
                for (i = 0; i < this.sections.length; i++) {
                    axios.put(
                        '/api/v1/polls/' + this.id + '/sections/' + this.sections[i].id + '/',
                        this.sections[i]
                    )
                }
            },

            deleteItem(id) {
                this.items = this.items.filter(item => item.id !== id);
            },
            addItem(newItem) {
                this.items = [...this.items, newItem];
            },
            isMobile() {
                return (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent));
            },
            updateSurveyItemsPossibilities() {
                const newItem = {
                    id: uuidv4(),
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
                };
                this.items = [...this.items, newItem];
            },
            updateSurveyItemsSelect() {
                const newItem = {
                    id: uuidv4(),
                    type: "dropdown",
                    question: "Was ist deine Lieblingsfarbe?",
                    possibilities: [
                        {
                            id: 1,
                            text: "Blau",
                        },
                        {
                            id: 2,
                            text: "Gelb",
                        },
                        {
                            id: 3,
                            text: "Rosa",
                        }
                    ]
                };
                this.items = [...this.items, newItem];
            },
            updateSurveyItemsText() {
                const newItem = {
                    id: uuidv4(),
                    type: "section",
                    question: "Was hast du heute geschafft?",
                };
                this.items = [...this.items, newItem];
            },
            updateSurveyItemsLimChar() {
                const newItem = {
                    id: uuidv4(),
                    type: "freetext",
                    question: "Was ist deine Lieblingsprogrammiersprache?",
                    possibilities: [
                        {
                            limit: 10
                        }
                    ]
                };
                this.items = [...this.items, newItem];
            },
            updateSurveyItemsX() {
                const newItem = {
                    id: uuidv4(),
                    type: "slider",
                    question: "Wie gerne magst du Brokkoli, von 1 bis 10?",
                    possibilities: [
                        {
                            min: 1,
                            max: 10,
                            step: 1
                        }
                    ]
                };
                this.items = [...this.items, newItem];
            },
            updateSurveyItemsCheckbox() {
                const newItem = {
                    id: uuidv4(),
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
                };
                this.items = [...this.items, newItem];
            },
            log: function (...e) {
                    console.log(...e);
            }
        },
        components: {
            AddQuestion,
            SurveyItem,
            NavBar,
            HelloWorld,
            draggable
        }
    }
</script>

<style scoped>

    .my-row {
        padding: 20px;
        align-content: center;
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
        color: #868686;
        font-size: 30px;
        text-align: center;
    }
</style>
