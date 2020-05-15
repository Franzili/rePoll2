<template>
    <div>
        <nav-bar></nav-bar>
        <div class="my-head">
            <div class="my-titel">
                Titel:
            </div>
            <div>
                Moby Dick
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
                    <b-col></b-col>
                    <b-col>Gliederung</b-col>
                </b-row>

                <!-- draggable palette items -->
                <b-row style="text-align:center;" class="my-row">
                    <b-col>
                        <div class="col-4">
                            <draggable
                                class="list-group" v-model="palette"
                                group="group"
                                @change="log"
                                v-on:end="updatePalette"
                            >
                                <div class="drag-item flex flex-justify-between">
                                    <!-- <b-form-input v-model="items[0].question"></b-form-input> -->
                                    <b-icon-square></b-icon-square>
                                    <!-- <AddQuestion v-on:add-item="addItem"/> -->
                                </div>
                            </draggable>
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
                                id: 3,
                                text: "bla bla asdikawzug l hif",
                            },
                            {
                                id: 4,
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
                                id: 5,
                                text: "bla bla asdikawzug l hif",
                            },
                            {
                                id: 6,
                                text: "yes",
                            }
                        ]
                    }
                ],
                palette: [
                    {
                        id: 8,
                        type: "checkbox",
                        question: "new question",
                        possibilities: [
                            {
                                id: 5,
                                text: "bla bla asdikawzug l hif",
                            },
                            {
                                id: 6,
                                text: "yes",
                            }
                        ]
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
                return (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent));
            },
            updatePalette() {
                const newItem = {
                    id: uuidv4(),
                    type: "checkbox",
                    question: "test",
                    possibilities: [
                        {
                            id: 5,
                            text: "bla bla asdikawzug l hif",
                        },
                        {
                            id: 6,
                            text: "yes",
                        }
                    ]
                };
                this.palette = [...this.palette, newItem];
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
