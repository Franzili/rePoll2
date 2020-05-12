<template>
    <div style="text-align:center;">
        <p>{{item.question}}</p>

        <!-- all possible answers possibilities -->
        <div v-if="item.type === 'checkbox'">
            <b-form-group>
                <b-form-checkbox-group>
                    <div class="text-left" v-bind:key="pos.id" v-for="pos in item.possibilities">
                        <b-form-checkbox>{{pos.text}}</b-form-checkbox>
                        <button v-if="edit === true" @click="delPos(pos.id)">x</button>
                    </div>
                </b-form-checkbox-group>
            </b-form-group>
        </div>

        <div v-if="item.type === 'radio'">
            <!-- TODO must one be selected? -->
            <b-form-group>
                <div class="text-left" v-bind:key="pos.id" v-for="pos in item.possibilities">
                    <b-form-radio>{{pos.text}}</b-form-radio>
                </div>
            </b-form-group>
        </div>

        <div v-if="item.type === 'freetext'">
            <b-form-input class="text-box"/>
            <b-form-input v-if="edit === true" placeholder="Set chracter limit..." class="text-box"/>
        </div>

        <div v-if="item.type === 'dropdown'">
            <b-dropdown class="drop-down" text="Dropdown Button">
                <div class="text-left" v-bind:key="pos.id" v-for="pos in item.possibilities">
                    <b-dropdown-item>{{pos.text}}</b-dropdown-item>
                </div>
            </b-dropdown>
        </div>

        <QuestionEditor v-if="edit === true && item.type !== 'freetext'" ref="editor" v-on:add-pos="addPos"/>

        <b-button class="my-btn" v-if="edit === true" @click="$emit('del-item', item.id)" variant="danger">delete</b-button>
        <p class="bottom-line"></p>
    </div>
</template>

<script>

    import QuestionEditor from "./QuestionEditor";

    export default {
        name: "SurveyItem",
        components: {QuestionEditor},
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

    .bottom-line {
        padding: 10px;
        border-bottom: 1px #000000 dotted;
    }

    .text-box {
        margin-bottom: 18px;
    }

    .drop-down {
        margin-bottom: 18px;
    }

    .my-btn {
        margin-top: 10px;
    }
</style>
