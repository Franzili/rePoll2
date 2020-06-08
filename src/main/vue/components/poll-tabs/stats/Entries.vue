<template>
    <div>
        <b-container>
            <b-col cols="8">
                <b-form-select v-model="selected" :options="getNames">
                    <template v-slot:first>
                        <b-form-select-option :value="null">Select a User</b-form-select-option>
                    </template>
                </b-form-select>
            </b-col>
        </b-container>

        <h6> Answers </h6>

        <div v-if="getEntries(selected)[0]">
            <div v-bind:key="answer.qId" v-for="answer in getEntries(selected)">
                <p> {{answer.answer}} </p>
            </div>
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
