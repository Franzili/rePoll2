<template>
    <div>
        <p>
            <b-form-select v-model="selected" :options="comboChoice"></b-form-select>
        </p>

        <p>
            <label>Input with datalist</label>
            <b-form-input list="input-list" id="input-with-list"></b-form-input>
            <b-form-datalist id="input-list" :options="comboChoice" v-model="selected"></b-form-datalist>
        </p>

        <p>
            <b-form-input list="my-list-id"></b-form-input>

            <datalist id="my-list-id">
                <option>Manual Option</option>
                <option></option>
            </datalist>
        </p>
        <p>
            Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et
            dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet
            clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet,
            consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed
            diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea
            takimata sanctus est Lorem ipsum dolor sit amet.
        </p>
        <p>
            Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et
            dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet
            clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet,
            consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed
            diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea
            takimata sanctus est Lorem ipsum dolor sit amet.
        </p>
        <p>
            Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et
            dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet
            clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet,
            consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed
            diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea
            takimata sanctus est Lorem ipsum dolor sit amet.
        </p>
        <p>
            Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et
            dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet
            clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet,
            consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed
            diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea
            takimata sanctus est Lorem ipsum dolor sit amet.
        </p>
    </div>
</template>

<script>

    import {mapGetters} from "vuex";

    export default {
        name: "Questions",
        data() {
            return {
                tmpID: 0,
                selected: null,
                comboChoice: [{value: null, text: 'Select your question'}],
                comboChoice2: [],
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
            this.fillComboChoice()
        },
        methods: {
            fillComboChoice() {
                for (var i = 0; i < this.poll.questions.length; i++) {
                    let choice = {value: this.poll.questions[i].id, text: this.poll.questions[i].title}
                    this.comboChoice = [... this.comboChoice, choice]
                    let choice2 = this.poll.questions[i].title
                    this.comboChoice2 = [... this.comboChoice2, choice2]
                }
            }
        }
    }
</script>

<style scoped>

</style>
