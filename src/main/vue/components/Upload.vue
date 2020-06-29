<template>
    <div>
        <h6>Import Questions from JSON File</h6>
        <UploadJSON v-on:uploadFinished="addToPollWithSections"></UploadJSON>
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
                    id: -1,
                    title: "is this working?",
                    questionOrder: 1000,
                    charLimit: 255
                },
                questionsFromFile: [],
                questionsForPoll: []
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

            prepareQuestionsWithSections(data) {
                let questionsFromFileJSON = JSON.parse(data);

                for (let i = 0; i < questionsFromFileJSON.length; i++) {
                    let sectionHeader = {
                        description: questionsFromFileJSON[i].description,
                        id: -1,
                        title: questionsFromFileJSON[i].title,
                        type: 'SectionHeader'
                    };
                    this.questionsForPoll.push(sectionHeader);

                    for (let j = 0; j < questionsFromFileJSON[i].questions.length; j++) {
                        let tmpQ = questionsFromFileJSON[i].questions[j];
                        tmpQ.id = -1;
                        this.questionsForPoll.push(tmpQ);
                    }

                }
            },

            async addToPollWithSections(data) {
                this.prepareQuestionsWithSections(data);

                let newPollStructure = this.pollStructure;
                for (let i = 0; i < this.questionsForPoll.length; i++) {
                    newPollStructure = this.pollStructure;
                    newPollStructure.push(this.questionsForPoll[i]);
                    await this.$store.dispatch('currentPoll/updateStructure', newPollStructure);
                }
            }

            //Use this if the sections get broken
            /*prepareQuestions() {
                let questionsFromFileJSON = JSON.parse(this.questionsFromFile);

                for (let i = 0; i < questionsFromFileJSON.length; i++) {
                    let tmpQ = questionsFromFileJSON[i];
                    tmpQ.id = -1;
                    this.questionsForPoll.push(tmpQ);
                }
            },

            async addToPoll(data) {

                this.questionsFromFile = data;

                this.prepareQuestions();

                let newPollStructure = this.pollStructure;
                for (let i = 0; i < this.questionsForPoll.length; i++) {
                    newPollStructure = this.pollStructure;
                    newPollStructure.push(this.questionsForPoll[i]);
                    await this.$store.dispatch('currentPoll/updateStructure', newPollStructure);
                }
            },*/
        }
    }
</script>

<style scoped>

</style>
