<template>
    <div>
        <UploadJSON v-on:uploadFinished="setQuestionsFromFile"></UploadJSON>
        <b-button class="mt-4" @click="addToPoll">Upload Test Question</b-button>
        <hr/>
        <p>{{questionsFromFile}}</p>
    </div>
</template>

<script>
    import UploadJSON from "./UploadJSON";
    export default {
        name: "Upload",
        components: {UploadJSON},
        data() {
            return {
                test: {
                    type: "TextQuestion",
                    id: 2065,
                    title: "is this working?",
                    questionOrder: 1000,
                    charLimit: 255
                },
                addedTestToPollStructure: [],
                questionsFromFile: []
            }
        },
        computed: {
            pollStructure: {
                get() {
                    return this.$store.getters["currentPoll/pollStructureFlat"];
                },
                set(value) {
                    this.$store.dispatch('currentPoll/updateStructure', value);
                }
            }
        },
        methods: {

            setQuestionsFromFile(data) {
                console.log("test");
                this.questionsFromFile = data;
            },

            addToPoll() {
                console.log(this.pollStructure);
                let addedTestToPollStructure = this.pollStructure;
                addedTestToPollStructure.push(this.test);
                console.log(addedTestToPollStructure);
                //this.$store.dispatch('currentPoll/updateStructure', addedTestToPollStructure);
                //console.log(this.$store.getters["currentPoll/pollStructureFlat"]);
            }
        }
    }
</script>

<style scoped>

</style>
