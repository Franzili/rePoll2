<template>
    <b-container>
        <b-row style="margin-top: 2vh">
            <b-col cols="4">
                <b-form-group description="Iteration launch date">
                    <b-form-select v-model="iteration"
                                   :options="iterationList"></b-form-select>
                </b-form-group>
            </b-col>
            <b-col cols="5">
                <b-form-select  v-model="selected" :options="getNames">
                    <template v-slot:first>
                        <b-form-select-option :value="null">Select a User</b-form-select-option>
                    </template>
                </b-form-select>
            </b-col>
        </b-row>

        <div v-if="getEntries(selected)[0]">
            <b-container>

                <div v-bind:key="section.section" v-for="section in getEntries(selected)">
                    <h6 class="section">{{section.section}}</h6>

                    <b-col>
                        <div v-if="section.idQA[0]">
                            <b-table
                                striped
                                hover
                                responsive
                                :fields="['question', 'answer']"
                                :items="section.idQA"
                            ></b-table>
                        </div>
                    </b-col>
                </div>
            </b-container>
        </div>
    </b-container>
</template>

<script>
    import {mapActions, mapGetters, mapState} from "vuex";

    export default {
        name: "Entries",
        props: ['iterationList'],
        data() {
            return {
                selected: null,
                structure: [],
                items: [],
                loaded: false
            }
        },
        async mounted() {
            this.loaded = false
            await this.loadEntries({pollId: this.$route.params.pollId, iterationId: this.iteration})
            this.structure = this.getEntries;
            this.loaded = true
        },
        computed: {
            iteration: {
                get() {
                    return this.$store.getters["currentPoll/getIterationId"];
                },
                set(val) {
                    this.$store.commit('currentPoll/setIterationId', val)
                }
            },
            ...mapState('currentPoll', {
                entries: 'entries'
            }),
            ...mapGetters('currentPoll', {
                getEntries: 'entriesWithSections',
                getNames: 'entriesUserNames'
            })
        },
        methods: {
            ...mapActions('currentPoll', {
                loadEntries: 'loadEntries',
            }),
            async helper(id) {
                await this.loadEntries({pollId: this.$route.params.pollId, iterationId: id})
                this.structure = this.getEntries;
            }
        },
        watch: {
            iteration: function (val) {
                this.helper(val)
            }
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
