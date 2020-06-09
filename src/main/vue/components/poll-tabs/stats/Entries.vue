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
                    <b-col><h6>Questions</h6></b-col>
                    <b-col><h6>Answers</h6></b-col>
                </b-row>

                <div v-bind:key="section.section" v-for="section in getEntries(selected)">
                    <h6>{{section.section}}</h6>
                    <div v-if="section.idQA[0]">
                        <div v-bind:key="idQA2.qId" v-for="idQA2 in section.idQA">
                            <b-row>
                                <b-col>
                                    <p> {{idQA2.question}} </p>
                                </b-col>
                                <b-col>
                                    <p> {{idQA2.answer}} </p>
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
            console.log('this.structure')
        },
        computed: {
            ...mapState('currentPoll', {
                entries: 'entries'
            }),
            ...mapGetters('currentPoll', {
                getEntries: 'entriesWithoutSection',
                getNames: 'entriesUserNames'
            })
        },
    }
</script>

<style scoped>

</style>
