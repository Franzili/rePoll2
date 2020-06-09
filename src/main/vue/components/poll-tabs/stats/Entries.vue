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
            <!--<b-container>
                <b-row>
                    <b-col><h6>Questions</h6></b-col>
                    <b-col><h6>Answers</h6></b-col>
                </b-row>
                <div v-bind:key="answer.qId" v-for="answer in getEntries(selected)">
                    <b-row>
                        <b-col>
                            <p> {{answer.question}} </p>
                        </b-col>
                        <b-col>
                            <p> {{answer.answer}} </p>
                        </b-col>
                    </b-row>
                </div>
            </b-container>-->

            <p>
                {{getEntries(selected)}}
            </p>

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
