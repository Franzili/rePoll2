<template>
    <div>
        <nav-bar></nav-bar>
        <div class="my-head">
            <div class="my-titel">
                Titel:
            </div>
            <div>
                Moby
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
                    <div>
                        <b-button v-b-toggle.sidebar-1>Add Question</b-button>
                        <b-sidebar id="sidebar-1" title="Add Questions" shadow>
                            <div class="px-3 py-2">
                                <b-img src="https://upload.wikimedia.org/wikipedia/commons/f/f5/Die_drei_fragezeichen.svg" fluid thumbnail></b-img>
                                <p>
                                    Add a Question into your Survey via Drag and Drop!
                                </p>

                                <div class="col-4">
                                    <draggable
                                        class="list-group" v-model="palette"
                                        group="group"
                                        @change="log"
                                        v-on:end="updateSurveyItemsX"
                                    >
                                    <div class="drag-item flex flex-justify-between">
                                        <!-- <b-form-input v-model="items[0].question"></b-form-input> -->
                                        <b-icon-question-square></b-icon-question-square>
                                        <b-text> Simple Question</b-text>
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
                                            <b-icon-square></b-icon-square>
                                            <b-text> Multiple-choice with Possibilities</b-text>
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
                                            <b-text> Select Answer with Possibility</b-text>
                                            <!-- <AddQuestion v-on:add-item="addItem"/> -->
                                        </div>
                                    </draggable>
                                    <draggable
                                        class="list-group" v-model="palette"
                                        group="group"
                                        @change="log"
                                        v-on:end="updateSurveyItemsText"
                                    >
                                        <div class="drag-item flex flex-justify-between">
                                            <!-- <b-form-input v-model="items[0].question"></b-form-input> -->
                                            <b-icon-type></b-icon-type>
                                            <b-text> Textquestion with Possibility</b-text>
                                            <!-- <AddQuestion v-on:add-item="addItem"/> -->
                                        </div>
                                    </draggable>
                                </div>


                            </div>
                        </b-sidebar>
                    </div>
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

    export default {
        name: "CreateSurvey",
        data() {
            return {
                edit: true,
                items: [
                ],
                palette: [
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
                return (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent));
            },
            updateSurveyItemsPossibilities() {
                const newItem = {
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
                            id: 1,
                            text: "Wie geht es dir Abends?",
                        }
                    ]
                };
                this.items = [...this.items, newItem];
            },
            updateSurveyItemsSelect() {
                const newItem = {
                    id: uuidv4(),
                    type: "dropdown",
                    question: "Select Answer with Possibility",
                    possibilities: [
                        {
                            id: 1,
                            text: "Meditierst du regelmäßig?",
                        },
                        {
                            id: 2,
                            text: "Machst du regelmäßig Sport?",
                        },
                        {
                            id: 3,
                            text: "Nimmst du regelmäßig kalte Duschen?",
                        }
                    ]
                };
                this.items = [...this.items, newItem];
            },
            updateSurveyItemsText() {
                const newItem = {
                    id: uuidv4(),
                    type: "section",
                    question: "Textquestion with Possibility",
                };
                this.items = [...this.items, newItem];
            },
            updateSurveyItemsX() {
                const newItem = {
                    id: uuidv4(),
                    type: "checkbox",
                    question: "Simple Question",
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
