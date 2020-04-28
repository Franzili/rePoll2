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
        <b-button type="submit" variant="success">add Question</b-button>
    </b-form>
</template>

<script>
    import { v4 as uuidv4 } from 'uuid';
    export default {
        name: "AddQuestion",
        data() {
            return {
                question: ''
            }
        },
        methods: {
            addItem(e) {
                e.preventDefault();
                const newItem = {
                    //TODO this id should be uniqe
                    id: uuidv4(),
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
