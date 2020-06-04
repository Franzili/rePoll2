<template>
    <div>
        <p>
            <b-row style="margin-top: 2vh">
                <b-col cols="4">
                    <SelectBox :first="questionHeader" v-on:getSelected="selected = $event"></SelectBox>
                </b-col>
                <b-col cols="4">
                    <SelectBox v-on:getSelected="selected = $event"></SelectBox>
                </b-col>
                <b-col cols="4"></b-col>
            </b-row>
        </p>


        <p> Hallo ich bin ausgewählt: {{selected}}</p>

        <p>{{answers}}</p>
        <p>{{match}}</p>

        <b-table :items="match"></b-table>

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

    import SelectBox from "./SelectBox";
    import {mapActions, mapState} from "vuex";

    export default {
        name: "Questions",
        data() {
            return {
                tmpID: 0,
                selected: null,
                questionHeader: {value: null, text: 'Select your question'},
                match: []
            }
        },
        computed: {
            ...mapState('currentPoll', {
                poll: 'poll',
                answers: 'answers'
            }),
        },
        watch: {
            selected: function (val) {
                let answerCmd = {poll: this.poll.id, quest: val}
                this.loadAnswers(answerCmd)
                this.match = []
                let funfun = Object.entries(this.answers)
                for (let i = 0; i < funfun.length; i++) {
                    let set = {Username: funfun[i][0], Answers: funfun[i][1].text}
                    this.match = [...this.match, set]
                }
            }
        },
        methods: {
            ...mapActions('currentPoll', {
                loadAnswers: 'loadAnswers'
            })
        },
        components: {
            SelectBox
        }
    }
</script>

<style scoped>

</style>
