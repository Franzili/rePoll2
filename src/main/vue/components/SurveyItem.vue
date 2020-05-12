<template>
    <div style="text-align:center;">
        <p class="question">{{item.question}}</p>

        <!-- all possible answers possibilities -->
        <div v-if="item.type === 'checkbox'">
            <b-form-group>
                <b-form-checkbox-group v-model="selected">
                    <div v-bind:key="pos.id" v-for="pos in item.possibilities">
                        <b-container>
                            <b-row>
                                <b-col class="text-left" cols="8"><b-form-checkbox :value="pos.text">{{pos.text}}</b-form-checkbox></b-col>
                                <b-col><button v-if="edit === true" @click="delPos(pos.id)">x</button></b-col>
                            </b-row>
                        </b-container>
                    </div>
                </b-form-checkbox-group>
            </b-form-group>
        </div>

        <div v-if="item.type === 'radio'">
            <b-form-group>
                <div v-bind:key="pos.id" v-for="pos in item.possibilities">
                    <b-container>
                        <b-row>
                            <b-col class="text-left" cols="8"><b-form-radio v-model="selected" :value="pos.text">{{pos.text}}</b-form-radio></b-col>
                            <b-col><button v-if="edit === true" @click="delPos(pos.id)">x</button></b-col>
                        </b-row>
                    </b-container>
                </div>
            </b-form-group>
        </div>

        <div v-if="item.type === 'freetext'">
            <!-- TODO enter new variable for text instead of array (v-model)? -->
            <b-form-input v-model="selected" class="text-box"/>
            <b-form-input v-if="edit === true" placeholder="Set chracter limit..." class="text-box"/>
        </div>

        <div v-if="item.type === 'dropdown'">
            <b-dropdown class="drop-down" text="select answer">
                <div class="text-left" v-bind:key="pos.id" v-for="pos in item.possibilities">
                    <!-- TODO how to set value -->
                    <b-dropdown-item v-model="selected" :value="pos.text">{{pos.text}}</b-dropdown-item>
                </div>
            </b-dropdown>
        </div>

        <QuestionEditor v-if="edit === true && item.type !== 'freetext'" ref="editor" v-on:add-pos="addPos"/>

        <b-button class="my-btn" v-if="edit === true" @click="$emit('del-item', item.id)" variant="danger">delete question</b-button>
    </div>
</template>

<script>

    import QuestionEditor from "./QuestionEditor";

    export default {
        name: "SurveyItem",
        components: {QuestionEditor},
        data() {
            return {
                selected: []
            }
        },
        props: ["item", "edit"],
        methods:{
            addPos(newPos){
                this.item.possibilities = [...this.item.possibilities, newPos];
            },
            delPos(id){
                this.item.possibilities = this.item.possibilities.filter(possibilitiy => possibilitiy.id !== id);
            },
            getPossibilities() {
                return this.possibilities;
            }
        }
    }
</script>

<style scoped>
    .text-box {
        margin-bottom: 18px;
    }

    .drop-down {
        margin-bottom: 18px;
    }

    .my-btn {
        margin-top: 15px;
        margin-bottom: 100px;
    }

    .question {
        font-weight: bold;
    }
</style>
