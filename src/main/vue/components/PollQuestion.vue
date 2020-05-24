<template>
    <div style="text-align:center;">
        <b-card border-variant="dark">
        <b-form-input v-if="editQuestion" class="question" v-model="question.title"></b-form-input>
        <p v-else class="question">{{question.title}}</p>
        <b-icon-check-all class="my-icon" scale="2" animation="fade" v-if="editQuestion" @click="changeEditQuestion"></b-icon-check-all>
        <b-icon-pencil class="my-icon" scale="1.5" v-else-if="edit" @click="changeEditQuestion"></b-icon-pencil>

        <!-- all possible answers possibilities -->
        <div v-if="question.type === 'checkbox'">

            <b-form-group>
                <b-form-checkbox-group v-model="selected">
                    <div v-bind:key="pos.id" v-for="pos in question.possibilities">
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

        <div v-if="question.type === 'radio'">
            <b-form-group>
                <div v-bind:key="pos.id" v-for="pos in question.possibilities">
                    <b-container>
                        <b-row>
                            <b-col class="text-left" cols="8"><b-form-radio v-model="selected" :value="pos.text">{{pos.text}}</b-form-radio></b-col>
                            <b-col><b-button class="del-pos-btn" variant="outline-secondary" pill v-if="edit === true" @click="delPos(pos.id)">x</b-button></b-col>
                        </b-row>
                    </b-container>
                </div>
            </b-form-group>
        </div>
        <div v-if="question.type === 'section'">
            <label>
                <textarea></textarea>
            </label>
        </div>

        <div v-if="question.type === 'freetext'">
            <!-- TODO enter new variable for text instead of array (v-model)? -->
            <b-form-input :maxlength="question.possibilities[0].limit" v-model="selected" class="text-box"/>

            <div v-if="edit && !editCharLimit">
                character limit:
                {{ question.possibilities[0].limit }}
                <b-icon-pencil class="freetext-pen" scale="1.5"  @click="changeEditCharLimit"></b-icon-pencil>
            </div>

            <div v-if="edit && editCharLimit">
                character limit:
                <b-form-input v-model="question.possibilities[0].limit" placeholder="Set character limit..." class="text-box"/>
                <b-icon-check-all class="my-icon" scale="2" animation="fade" @click="changeEditCharLimit"></b-icon-check-all>
            </div>
        </div>

        <div v-if="question.type === 'dropdown'">
            <b-dropdown class="drop-down" text="select answer">
                <div class="text-left" v-bind:key="pos.id" v-for="pos in question.possibilities">
                    <!-- TODO how to set value -->
                    <b-dropdown-item v-model="selected" :value="pos.text">{{pos.text}}</b-dropdown-item>
                </div>
            </b-dropdown>
            <b-icon-x-circle-fill class="dropdown-icon" scale="2" v-if="edit && !editDropdown" variant="secondary" @click="changeEditDropdown">delete possibilities</b-icon-x-circle-fill>
            <b-icon-check-all class="dropdown-icon" scale="2" animation="fade" v-if="edit && editDropdown" @click="changeEditDropdown"></b-icon-check-all>

            <div v-if="editDropdown">
                <div v-bind:key="pos.id" v-for="pos in question.possibilities">
                    <b-container>
                        <b-row>
                            <b-col class="text-left" cols="8">{{pos.text}}</b-col>
                            <b-col><b-button class="del-pos-btn" variant="outline-secondary" pill v-if="edit === true" @click="delPos(pos.id)">x</b-button></b-col>
                        </b-row>
                    </b-container>
                </div>
            </div>
        </div>

        <div v-if="question.type === 'slider'">
            <div class="slider">
                {{ val.toFixed(2) }}
            </div>

            <b-form-input
                id="bg-opacity"
                v-model="val"
                type="range"
                number
                :min="question.possibilities[0].min"
                :max="question.possibilities[0].max"
                :step="question.possibilities[0].step"
            ></b-form-input>

            <b-icon-pencil class="my-icon" scale="1.5" v-if="edit && !editSlider" @click="changeEditSlider"></b-icon-pencil>

            <div v-if="edit && editSlider">

                min-value:
                <b-form-input v-model="question.possibilities[0].min"></b-form-input>
                max-value:
                <b-form-input v-model="question.possibilities[0].max"></b-form-input>
                stepsize:
                <b-form-input class="my-form" v-model="question.possibilities[0].step"></b-form-input>

                <b-icon-check-all class="my-icon" scale="2" animation="fade" @click="changeEditSlider"></b-icon-check-all>
            </div>
        </div>

        <QuestionEditor v-if="edit === true && question.type !== 'freetext' && question.type !== 'slider' && question.type !== 'section'" ref="editor" v-on:add-pos="addPos"/>

        <b-button class="my-btn" v-if="edit === true" @click="$emit('del-question', question.id)" pill variant="outline-secondary">delete question</b-button>
            </b-card>
        <div v-if="!edit" class="my-test">

        </div>
    </div>
</template>

<script>

    import QuestionEditor from "./QuestionEditor";

    export default {
        name: "PollQuestion",
        components: {QuestionEditor},
        data() {
            return {
                editQuestion: false,
                editSlider: false,
                editCharLimit: false,
                editDropdown: false,
                val: this.question.possibilities[0].min,
                selected: []
            }
        },
        props: ["question", "edit"],
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
            changeEditDropdown() {
                this.editDropdown = !this.editDropdown;
            },
            addPos(newPos){
                this.question.possibilities = [...this.question.possibilities, newPos];
            },
            delPos(id){
                this.question.possibilities = this.question.possibilities.filter(possibility => possibility.id !== id);
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

    .dropdown-icon {
        margin-left: 20px;
    }

    .my-btn {
        margin-top: 15px;
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

    .my-test {
        margin-bottom: 50px;
    }
</style>
