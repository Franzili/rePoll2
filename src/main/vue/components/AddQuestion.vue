<template>
    <b-form @submit="addItem">
        <div style="text-align:left;">
            <b-form-group
                label="Enter new question:">
                <b-form-input
                    id="input-1"
                    required
                    placeholder="Question"
                    v-model="question"
                ></b-form-input>
            </b-form-group>

            <b-card-text class="my-margin-19">Select answer type:</b-card-text>
            <b-form-select v-model="type">
                <option value="freetext">Free text</option>
                <option value="checkbox">Checkbox</option>
                <option value="radio">Radio Button</option>
                <option value="dropdown">Drop down</option>
            </b-form-select>


            <QuestionEditor v-if="type !== 'freetext'" v-bind:type="type"/>
        </div>

        <b-button class="my-margin-18" type="submit" variant="success">add Question</b-button>
    </b-form>
</template>

<script>
    import { v4 as uuidv4 } from 'uuid';
    import QuestionEditor from "./QuestionEditor";
    export default {
        name: "AddQuestion",
        components: {QuestionEditor},
        data() {
            return {
                question: '',
                type: 'freetext'
            }
        },
        methods: {
            addItem(e) {
                e.preventDefault();
                const newItem = {
                    id: uuidv4(),
                    type: this.type,
                    question: this.question
                };
                this.$emit('add-item', newItem);

                this.question = '';
            }
        }
    }
</script>

<style scoped>
    .my-margin-18 {
        margin-top: 18px;
    }

    .my-margin-19 {
        margin-top: 40px;
    }
</style>
