<template>
    <div>
        <b-card-text class="my-margin">Possible answers:</b-card-text>
        <div v-bind:key="possibility.id" v-for="possibility in possibilities">
            <Possibility v-bind:possibility="possibility" v-on:del-pos="delPos"/>
        </div>
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

            <b-button class="my-margin" type="submit" variant="outline-primary">+</b-button>

        </b-form>
    </div>
</template>

<script>
    import { v4 as uuidv4 } from 'uuid';
    import Possibility from "./Possibility";

    export default {
        name: "QuestionEditor",
        components: {Possibility},
        data() {
            return {
                newPosText: '',
                possibilities: []
            }
        },
        methods: {
            addPossibility(e) {
                e.preventDefault();
                const newPos = {
                    id: uuidv4(),
                    text: this.newPosText
                };
                this.possibilities = [...this.possibilities, newPos];

                this.newPosText = '';
            },
            delPos(id) {
                this.possibilities = this.possibilities.filter(possibilitiy => possibilitiy.id !== id);
            },
            resetPossibilities(){
                this.possibilities = [];
            },
            getPossibilities(){
                return this.possibilities;
            }
        },
        props: ["type"]
    }
</script>

<style scoped>
    .my-margin {
        margin-top: 18px;
    }
</style>
