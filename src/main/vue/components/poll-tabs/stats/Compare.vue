<template>
    <b-container>

        <b-button @click="showModal" > lalaa</b-button>
        <myModal ref="mymodal" v-bind:modalObj="modal"></myModal>

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
    </b-container>
</template>

<script>
    import {mapState} from "vuex";
    import myModal from "./myModal";

    export default {
        name: "Compare",
        data() {
            return {
                modal: [
                    {
                    section: {name: 'popel', id: 1},
                    questions: [
                        {value: '1', text: 'Wie alt bist du sdsadf sf saer fesf fes hhhfe sesfseffuw fewfewfwe fwefewfwefw fewfwef fewf wef ewf ewf we fwf'},
                        {value: '2', text: 'Wie alt ISsT du?'},
                        {value: '3', text: 'Wie alt Tisb du?'},
                        {value: '4', text: 'Wie alt sbti du?'},
                    ]},
                    {
                        section: {name: 'popelhoch3', id: 2},
                        questions: [
                            {value: '5', text: 'Wie alt fefewfbist du?'},
                            {value: '6', text: 'Wie al432424t ISsT du?'},
                            {value: '7', text: 'Wie altf23f2 Tisb du?'},
                            {value: '8', text: 'Wie alt sbjjufshfewti du?'},
                        ]
                    }]
            }
        },
        computed: {
            ...mapState('currentPoll', {
                statistics: 'statistics'
            })
        },
        methods: {
            showModal() {
                this.$refs.mymodal.show()
            },
        },
        components: {
            myModal
        }
    }
</script>

<style scoped>

</style>
