<template>
    <div>
        <nav-bar></nav-bar>
        <HelloWorld style="text-align:center;" class="ml-auto" msg="Create your own Survey!"/>

        <!-- better for mobile version -->
        <div v-if="isMobile()">
            <SurveyItemList v-bind:items="items" v-on:del-item="deleteItem"/>
            <HelloWorld style="text-align:center;" class="ml-auto" msg=""/>
            <AddQuestion v-on:add-item="addItem"/>
        </div>

        <!-- better for Desktop version -->
        <div v-else>
            <b-container class="my-container">

                <!-- headers -->
                <b-row style="text-align:center;" class="my-row" cols="3">
                    <b-col>Edit Area</b-col>
                    <b-col>Surveyname</b-col>
                    <b-col>Edit elements</b-col>
                </b-row>

                <!-- draggable palette items -->
                <b-row style="text-align:center;" class="my-row">
                    <b-col>
                        <div class="col-1">
                            <draggable
                                class="list-group" :list="palette"
                                :group="{ name: 'group', pull: 'clone', put: false }"
                                @change="log"
                            >
                                <div
                                    class="drag-item flex flex-justify-betweeen"
                                >
                                    <b-form-input
                                        id="Text"
                                        required
                                        placeholder="T"
                                    ></b-form-input>
                                </div>
                            </draggable>
                        </div>
                    </b-col>

                    <!-- draggable questions inside the poll -->
                    <b-col>
                        <div class="col-14">
                            <draggable
                                class="list-group" :list="items"
                                group="group"
                                @change="log"
                            >
                                <div
                                    v-for="item in items" :key="item"
                                    class="drag-item flex flex-justify-betweeen"
                                >
                                    <div>
                                        <SurveyItem v-bind:item="item" v-on:del-item="deleteItem"/>
                                    </div>
                                </div>
                            </draggable>
                        </div>

                        <HelloWorld class="ml-auto" msg=""/>
                        <AddQuestion v-on:add-item="addItem"/>
                    </b-col>
                    <b-col>
                    </b-col>
                </b-row>

                <rawDisplayer class="col-3" :value="items" title="List 1" />

                <rawDisplayer class="col-3" :value="palette" title="List 2" />

            </b-container>
        </div>
    </div>
</template>

<script>

    import draggable from "vuedraggable"
    import HelloWorld from '../components/HelloWorld.vue'
    import NavBar from "../components/NavBar";
    import SurveyItemList from "../components/SurveyItemList";
    import AddQuestion from "../components/AddQuestion";
    import SurveyItem from "../components/SurveyItem";

    export default {
        name: "CreateSurvey",
        components: {
            draggable,
            AddQuestion,
            SurveyItemList,
            NavBar,
            HelloWorld,
            SurveyItem
        },
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
                ],
                palette: [
                    {
                        question: "Text"
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
            log: function(...e) {
                console.log(...e);
            }
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
    .bigger-area > span {
        min-height: 40vh;
        display: block;
    }
    #text {

    }

</style>
