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

                <div v-bind:key="section.section" v-for="section in getEntries(selected)">
                    <h6 class="section">{{section.section}}</h6>

                    <b-col>
                        <div v-if="section.idQA[0]">
                            <b-table striped hover :fixed="true" :fields="['question', 'answer']" :items="section.idQA"></b-table>
                        </div>
                    </b-col>
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
                structure: [],
                items: []
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
        }
    }
</script>

<style scoped>

    .section {
        margin-top: 15px;
        margin-bottom: 15px;
        color: #3eab37;
    }
</style>
