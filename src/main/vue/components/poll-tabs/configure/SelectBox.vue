<template>
    <div>
        <b-form-select
            v-model="selected"
            v-on:change="$emit('getSelected', selected)"
            :options="comboChoice"></b-form-select>
    </div>
</template>

<script>
    import {mapState} from "vuex";

    export default {
        name: "SelectBox",
        data() {
            return {
                selected: null,
                comboChoice: []
            }
        },
        computed: {
            ...mapState('currentPoll', {
                pollQuestions: state => state.poll.questions
            }),
        },
        created: function () {
            this.comboChoice = [... this.comboChoice, this.first]
            this.fillComboChoice()
        },
        methods: {
            fillComboChoice() {
                for (var i = 0; i < this.pollQuestions.length; i++) {
                    let choice = {text: this.pollQuestions[i].title, value: this.pollQuestions[i].id}
                    this.comboChoice = [... this.comboChoice, choice]
                }
            }
        },
    }
</script>

<style scoped>

</style>
