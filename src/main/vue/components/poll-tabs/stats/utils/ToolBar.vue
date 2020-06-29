<template>
    <b-row>
        <b-button-toolbar v-if="actives !== undefined">
            <b-form-group>
                <b-form-select
                    v-if="actives[4] !== null && choices !== undefined"
                    style="max-width: 15rem; min-width: 15rem; margin-right: 0.5rem"
                    :disabled="actives[4] === false"
                    v-model="selected.question"
                    :options="choices"
                    v-on:change="$emit('question', selected.question)">
                </b-form-select>
            </b-form-group>

            <b-form-group>
                <b-form-select
                    style="max-width: 20rem;"
                    :disabled="actives[0] === false"
                    v-model="selected.frequency"
                    v-if="actives[0] !== null"
                    v-on:change="$emit('frequency', selected.frequency)">
                    <b-form-select-option value="abs">absolute frequency</b-form-select-option>
                    <b-form-select-option value="rel">relative frequency</b-form-select-option>
                </b-form-select>
            </b-form-group>

            <b-form-group>
                <b-button-group
                    style="margin-left: 0.5rem"
                    class="mr-1">
                    <b-button
                        variant="outline-secondary"
                        v-on:click="$emit('chart', 'table')"
                        :disabled="actives[8] === false"
                        v-if="actives[8] !== null"
                        title="table-chart">
                        <b-icon icon="pen"></b-icon>
                    </b-button>
                    <b-button
                        variant="outline-secondary"
                        v-on:click="$emit('chart', 'bar')"
                        :disabled="actives[1] === false"
                        v-if="actives[1] !== null"
                        title="bar-chart">
                        <b-icon icon="bar-chart-fill"></b-icon>
                    </b-button>
                    <b-button
                        variant="outline-secondary"
                        v-on:click="$emit('chart', 'donut')"
                        :disabled="actives[2] === false"
                        v-if="actives[2] !== null"
                        title="donut-chart">
                        <b-icon icon="pie-chart-fill"></b-icon>
                    </b-button>
                    <b-button
                        variant="outline-secondary"
                        v-on:click="$emit('chart', 'boxplot')"
                        :disabled="actives[3] === false"
                        v-if="actives[3] !== null"
                        title="boxplot">
                        <b-iconstack>
                            <b-icon stacked shift-h="10" icon="dash"></b-icon>
                            <b-icon stacked shift-h="" icon="vr"></b-icon>
                            <b-icon stacked shift-h="-10" icon="dash"></b-icon>
                        </b-iconstack>
                    </b-button>
                </b-button-group>
            </b-form-group>

            <b-form-group>
                <b-button
                    variant="outline-secondary"
                    v-if="actives[5] !== null"
                    title="mege tables"
                    v-on:click="setMergeState">
                    <b-icon v-if="merged" icon="chevron-contract"></b-icon>
                    <b-icon v-else icon="chevron-expand"></b-icon>
                </b-button>
            </b-form-group>
        </b-button-toolbar>


        <b-button-toolbar style="margin-left: auto" v-if="actives !== undefined">
            <b-form-group>
                <b-button-group :size="size">
                    <b-button
                        variant="outline-secondary"
                        v-on:click="$emit('edit')"
                        v-if="actives[7] !== null"
                        title="edit comparision">
                        <b-icon icon="pencil"></b-icon>
                    </b-button>
                    <b-button
                        variant="outline-secondary"
                        v-on:click="$emit('close')"
                        v-if="actives[6] !== null"
                        title="close comparision">
                        <b-icon icon="trash"></b-icon>
                    </b-button>
                </b-button-group>
            </b-form-group>
        </b-button-toolbar>
    </b-row>
</template>

<script>
    export default {
        name: "ToolBar",
        // actives: dataChange, bar, donut, boxplot, question, merge, delete, edit
        props: ['actives', 'choices', 'frequency', 'question', 'eSize'],
        data() {
            return {
                selected: {
                    question: '',
                    frequency: ''
                },
                merged: true,
                size: 'md'
            }
        },
        created() {
            this.selected.frequency = this.frequency
            if (this.eSize !== undefined) {
                this.size = this.eSize
            }
            if (this.choices !== undefined) {
                this.selected.question = this.choices[0].value
                this.$emit('question', this.selected.question)
            }
        },
        methods: {
            setMergeState() {
                this.$emit('merge', this.merged)
                this.merged = this.merged !== true
            },
            setFirst(qId) {
                this.selected.question = qId
                this.$emit('question', this.selected.question)
            }
        },
        watch: {
            question: function (newQuestion) {
                this.selected.question = newQuestion
            },
        }
    }
</script>

<style scoped>

</style>
