<template>
    <b-form style="text-align:center;" @submit="addItem">
        <b-form-group
            label="Enter new question:">
            <b-form-input
                id="input-1"
                required
                placeholder="Question"
                v-model="question"
            ></b-form-input>
        </b-form-group>

        <b-form-select v-model="type">
            <option value="freetext">Free text</option>
            <option value="checkbox">Checkbox</option>
            <option value="radio">Radio Button</option>
            <option value="dropdown">Drop down</option>
        </b-form-select>

        <b-button type="submit" variant="success">add Question</b-button>
    </b-form>
</template>

<script>
    import { v4 as uuidv4 } from 'uuid';
    export default {
        name: "AddQuestion",
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

</style>
