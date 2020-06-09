<template>
    <div>
        <b-container>
            <b-col cols="8">
                <b-form-select style="margin: 20px" v-model="selected" :options="getNames">
                    <template v-slot:first>
                        <b-form-select-option :value="null">Select a User</b-form-select-option>
                    </template>
                </b-form-select>
            </b-col>
        </b-container>

        <div v-if="getEntries(selected)[0]">
            <b-container>
                <b-row>
                    <b-col><h6 class="my-header">Questions</h6></b-col>
                    <b-col><h6 class="my-header">Answers</h6></b-col>
                </b-row>

                <div v-bind:key="section.section" v-for="section in getEntries(selected)">
                    <h6 class="section">{{section.section}}</h6>
                    <div v-if="section.idQA[0]">
                        <div v-bind:key="idQA2.qId" v-for="idQA2 in section.idQA">
                            <b-row>
                                <b-col>
                                    <p> {{idQA2.question}} </p>
                                    <hr>
                                </b-col>
                                <b-col>
                                    <div v-if="multibleAnswers(idQA2.answer)">
                                        <div v-bind:key="answer" v-for="answer in idQA2.answer">
                                            {{answer}}
                                        </div>
                                    </div>
                                    <p v-else> {{idQA2.answer}} </p>
                                    <hr>
                                </b-col>
                            </b-row>
                        </div>
                    </div>
                </div>
            </b-container>
        </div>
    </div>
</template>

<script>
    import {mapGetters, mapState} from "vuex";

    export default {
        name: "Entries",
        data() {
            return {
                selected: null,
                structure: []
            }
        },
        mounted() {
            this.structure = this.getEntries;
        },
        computed: {
            ...mapState('currentPoll', {
                entries: 'entries'
            }),
            ...mapGetters('currentPoll', {
                getEntries: 'entriesWithSections',
                getNames: 'entriesUserNames'
            })
        },
        methods: {
            multibleAnswers(answer) {
                if (answer === undefined || answer[0] === undefined) {
                    return false;
                }
                return answer[0].length !== 1;
            }
        }
    }
</script>

<style scoped>

    .my-header {
        font-size: 23px;
    }

    .section {
        margin-top: 15px;
        margin-bottom: 15px;
        color: #3eab37;
    }
</style>
