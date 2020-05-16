<template>
    <div>
        <b-form style="text-align:center;" @submit="addPossibility">

            <b-form-group class="my-margin"
                label="Enter new Possibility:">
                <b-form-input
                    id="input-1"
                    required
                    placeholder="New Possibility"
                    v-model="newPosText"
                ></b-form-input>
            </b-form-group>

            <b-button type="submit" pill variant="outline-primary">+</b-button>

        </b-form>
    </div>
</template>

<script>
    import { v4 as uuidv4 } from 'uuid';

    export default {
        name: "QuestionEditor",
        data() {
            return {
                newPosText: ''
            }
        },
        methods: {
            addPossibility(e) {
                e.preventDefault();
                const newPos = {
                    id: uuidv4(),
                    text: this.newPosText
                };

                this.$emit('add-pos', newPos);

                this.newPosText = '';
            }
        }
    }
</script>

<style scoped>
</style>
