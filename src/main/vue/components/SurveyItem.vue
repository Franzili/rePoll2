<template>
    <div style="text-align:center;">
        <b-form-input v-if="editQuestion" class="question" v-model="item.question"></b-form-input>
        <p v-else class="question">{{item.question}}</p>
        <b-icon-check-all class="my-icon" scale="2" animation="fade" v-if="editQuestion" @click="changeEditQuestion"></b-icon-check-all>
        <b-icon-pencil class="my-icon" scale="1.5" v-else-if="edit" @click="changeEditQuestion"></b-icon-pencil>

        <!-- all possible answers possibilities -->
        <div v-if="item.type === 'checkbox'">
            <b-form-group>
                <b-form-checkbox-group v-model="selected">
                    <div v-bind:key="pos.id" v-for="pos in item.possibilities">
                        <b-container>
                            <b-row>
                                <b-col class="text-left" cols="8"><b-form-checkbox :value="pos.text">{{pos.text}}</b-form-checkbox></b-col>
                                <b-col><b-button class="del-pos-btn" variant="outline-secondary" pill v-if="edit === true" @click="delPos(pos.id)">x</b-button></b-col>
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
                            <b-col><b-button class="del-pos-btn" variant="outline-secondary" pill v-if="edit === true" @click="delPos(pos.id)">x</b-button></b-col>
                        </b-row>
                    </b-container>
                </div>
            </b-form-group>
        </div>
        <div v-if="item.type === 'section'">
            <textarea></textarea>
        </div>

        <div v-if="item.type === 'freetext'">
            <!-- TODO enter new variable for text instead of array (v-model)? -->
            <b-form-input :maxlength="item.possibilities[0].limit" v-model="selected" class="text-box"/>

            <div v-if="edit && !editCharLimit">
                character limit:
                {{ item.possibilities[0].limit }}
                <b-icon-pencil class="freetext-pen" scale="1.5"  @click="changeEditCharLimit"></b-icon-pencil>
            </div>

            <div v-if="edit && editCharLimit">
                character limit:
                <b-form-input v-model="item.possibilities[0].limit" placeholder="Set chracter limit..." class="text-box"/>
                <b-icon-check-all class="my-icon" scale="2" animation="fade" @click="changeEditCharLimit"></b-icon-check-all>
            </div>
        </div>

        <div v-if="item.type === 'dropdown'">
            <b-dropdown class="drop-down" text="select answer">
                <div class="text-left" v-bind:key="pos.id" v-for="pos in item.possibilities">
                    <!-- TODO how to set value -->
                    <b-dropdown-item v-model="selected" :value="pos.text">{{pos.text}}</b-dropdown-item>
                </div>
            </b-dropdown>
        </div>

        <div v-if="item.type === 'slider'">
            <div class="slider">
                {{ val.toFixed(2) }}
            </div>

            <b-form-input
                id="bg-opacity"
                v-model="val"
                type="range"
                number
                :min="item.possibilities[0].min"
                :max="item.possibilities[0].max"
                :step="item.possibilities[0].step"
            ></b-form-input>

            <b-icon-pencil class="my-icon" scale="1.5" v-if="edit && !editSlider" @click="changeEditSlider"></b-icon-pencil>

            <div v-if="edit && editSlider">

                min-value:
                <b-form-input v-model="item.possibilities[0].min"></b-form-input>
                max-value:
                <b-form-input v-model="item.possibilities[0].max"></b-form-input>
                stepsize:
                <b-form-input class="my-form" v-model="item.possibilities[0].step"></b-form-input>

                <b-icon-check-all class="my-icon" scale="2" animation="fade" @click="changeEditSlider"></b-icon-check-all>
            </div>
        </div>

        <QuestionEditor v-if="edit === true && item.type !== 'freetext' && item.type !== 'slider' && item.type !== 'section'" ref="editor" v-on:add-pos="addPos"/>

        <b-button class="my-btn" v-if="edit === true" @click="$emit('del-item', item.id)" pill variant="outline-secondary">delete question</b-button>
    </div>
</template>

<script>

    import QuestionEditor from "./QuestionEditor";

    export default {
        name: "SurveyItem",
        components: {QuestionEditor},
        data() {
            return {
                editQuestion: false,
                editSlider: false,
                editCharLimit: false,
                val: this.item.possibilities[0].min,
                selected: []
            }
        },
        props: ["item", "edit"],
        methods:{
            changeEditQuestion() {
                this.editQuestion = !this.editQuestion;
            },
            changeEditSlider() {
                this.editSlider = !this.editSlider;
            },
            changeEditCharLimit() {
                this.editCharLimit = !this.editCharLimit;
            },
            addPos(newPos){
                this.item.possibilities = [...this.item.possibilities, newPos];
            },
            delPos(id){
                this.item.possibilities = this.item.possibilities.filter(possibilitiy => possibilitiy.id !== id);
            }
        }
    }
</script>

<style scoped>
    .text-box {
        margin-top: 10px;
        margin-bottom: 18px;
    }

    .drop-down {
        margin-top: 10px;
        margin-bottom: 18px;
    }

    .my-icon {
        margin-bottom: 10px;
    }

    .freetext-pen {
        margin-left: 10px;
    }

    .my-btn {
        margin-top: 15px;
        margin-bottom: 100px;
    }

    .slider {
        margin-top: 15px;
    }

    .del-pos-btn {
        padding: 3px 8px;
        font-size: 10px;
    }

    .question {
        font-weight: bold;
        margin-bottom: 10px;
    }

    .my-form {
        margin-bottom: 10px;
    }
</style>
