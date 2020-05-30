<template>
    <div>
        <b-form-select
            v-model="selected"
            v-on:change="$emit('getSelected', selected)"
            :options="comboChoice"></b-form-select>
    </div>
</template>

<script>
    import {mapGetters} from "vuex";

    export default {
        name: "SelectBox",
        porps: ['first'],
        data() {
            return {
                tmpID: 0,
                selected: null,
                comboChoice: []
            }
        },
        computed: {
            ...mapGetters(['getPoll']),
            poll() {
                return this.getPoll(this.tmpID)
            }
        },
        created: function () {
            this.tmpID = this.$route.params.tmpPollID;
            this.comboChoice = [... this.comboChoice, this.first]
            this.fillComboChoice()
        },
        methods: {
            fillComboChoice() {
                for (var i = 0; i < this.poll.questions.length; i++) {
                    let choice = {text: this.poll.questions[i].title, value: this.poll.questions[i].id}
                    this.comboChoice = [... this.comboChoice, choice]
                }
            }
        },
    }
</script>

<style scoped>

</style>
