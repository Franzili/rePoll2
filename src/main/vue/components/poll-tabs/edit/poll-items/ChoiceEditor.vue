<template>
    <p>
        <ul>
            <li v-bind:key="choice.id" v-for="choice in choices">
                <span class="choice-item">
                    <b-input v-model="choice.text"></b-input>

                    <b-button size="sm"
                              pill
                              variant="outline-secondary"
                              @click="deleteChoice(choice.id)"
                              :disabled="choices.length <= 1">
                        <b-icon-trash-fill/>
                    </b-button>
                </span>
            </li>
            <li>
                <b-button size="sm" @click="addChoice">
                    <b-icon-plus></b-icon-plus>
                </b-button>
            </li>
        </ul>
    </p>
</template>

<script>
    export default {
        name: "ChoiceEditor",
        props: {
            choices: {
                required: true,
                default: []
            }
        },
        methods: {
            addChoice() {
                // we need to assign an id temporarily until the backend has given us one.
                console.log(this.choices.map(choice => choice.id))
                let newChoiceId = Math.max(...this.choices.map(choice => choice.id)) + 1
                this.choices.push({
                    text: "",
                    id: newChoiceId
                });
            },
            deleteChoice(id) {
                console.log(this.choices)
                let choiceIndex = this.choices.findIndex(choice => choice.id === id);
                console.log(choiceIndex);
                if (choiceIndex === -1) {
                    console.warn(`[RePoll] Could not find choice with id ${id}`);
                    return;
                }
                this.choices.splice(choiceIndex, 1);
            }
        },
        watch: {
            choices: {
                handler: function() {
                    this.$emit('choicesChanged', this.choices);
                },
                deep: true
            }
        }
    }
</script>

<style scoped>
    .choice-item {
        display: flex;
    }
    .choice-item > input {
        flex-grow: 1;
        margin-right: 10px;
    }
    li:not(:last-child) {
        margin-bottom: 5px;
    }
</style>
