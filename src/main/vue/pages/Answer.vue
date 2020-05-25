<template>
    <div style="text-align:center;">
    <b-container class="mt-3">
        <HelloWorld></HelloWorld>
        <b-row>
            <b-col>
                    <b-form @submit.prevent="savePollEntry">
                        <b-form-group>
                            <p class="my-name">{{poll.title}}</p>
                        </b-form-group>
                        <b-row>
                            <b-col></b-col>
                            <b-form-group>

                                <div :key="question.id" v-for="question in this.poll.questions">

                                <!--
                                <div :key="item.id" v-for="item in items">
                                    -->
                                    <PollQuestion v-bind:question="question" v-bind:edit="edit"/>
                                </div>
                            </b-form-group>
                            <b-col></b-col>
                        </b-row>
                        <!--
                        template save buttons!!!
                        <b-button variant="primary" type="submit">Speichern</b-button>
                        -->
                        <b-button @click="savePollEntry">Save</b-button>
                        <!--
                        Submit Button for later
                        Final Submit, then answers can't be edited animore
                        <b-button class="my-button" variant="success">Submit!</b-button>
                        -->
                    </b-form>
            </b-col>
        </b-row>
    </b-container>
    </div>
</template>


<script>
    import HelloWorld from "../components/HelloWorld";
    import {mapGetters, mapMutations} from "vuex";
    import PollQuestion from "../components/PollQuestion"; //mapMutations

    export default {
        name: "Answer",
        data() {
            return {
                id: 0,
                questions: [],
            }
        },
        created() {
            this.id = this.$route.params.id
            this.poll = this.getPoll(this.id)
            //this.setQuestion()
        },
        computed: {
            ...mapGetters(['getPoll'])

    },
    methods: {
        isMobile() {
            return (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent));
        },
        ...mapMutations([
            'updatePoll'
        ]),
        savePollEntry() {
            this.updatePoll(this.poll)
            this.$router.push('/') //redirect to page '', here start page
        }
    },
    components: {
        HelloWorld,
        PollQuestion
        //,
        //SurveyItemList
    },
    }

</script>

<style scoped>
</style>
