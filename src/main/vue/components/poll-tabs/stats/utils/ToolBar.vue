<template>
    <div>
        <b-button-toolbar v-if="actives !== undefined">

            <b-form-group>
                <!-- && choices !== undefined-->
                <b-form-select
                    v-if="actives[4] !== null && choices !== undefined"
                    style="max-width: 30rem; min-width: 20rem"
                    v-model="selected.question"
                    :options="choices"
                    v-on:change="$emit('question', selected.question)">
                </b-form-select>
            </b-form-group>


            <b-form-group>
                <b-form-select
                    style="max-width: 20rem; margin-left: 0.25rem"
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
                        v-on:click="$emit('chart', 'bar')"
                        v-if="actives[1] !== null"
                        title="bar-chart">
                        <b-icon icon="bar-chart-fill"></b-icon>
                    </b-button>
                    <b-button
                        variant="outline-secondary"
                        v-on:click="$emit('chart', 'donut')"
                        v-if="actives[2] !== null"
                        title="donut-chart">
                        <b-icon icon="pie-chart-fill"></b-icon>
                    </b-button>
                    <b-button
                        variant="outline-secondary"
                        v-on:click="$emit('chart', 'boxplot')"
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

            <b-form-group>
                <b-button
                    variant="outline-secondary"
                    v-on:click="$emit('close')"
                    style="margin-left: 0.5rem"
                    v-if="actives[6] !== null"
                    title="close comparision">
                    <b-icon icon="trash"></b-icon>
                </b-button>
            </b-form-group>

            <b-form-group>
                <b-button
                    variant="outline-secondary"
                    v-on:click="$emit('edit')"
                    style="margin-left: 0.5rem"
                    v-if="actives[7] !== null"
                    title="edit comparision">
                    <b-icon icon="pencil"></b-icon>
                </b-button>
            </b-form-group>

        </b-button-toolbar>
    </div>
</template>

<script>
    export default {
        name: "ToolBar",
        // actives: dataChange, bar, donut, boxplot, question, merge, delete
        // TODO prop to parse frequency choice
        props: ['actives', 'choices','frequency'],
        data() {
            return {
                selected: {
                    question: '',
                    frequency: ''
                },
                merged: true,
            }
        },
        created() {
            this.selected.frequency = this.frequency
            if (this.choices !== undefined) {
                this.selected.question = this.choices[0].value
                this.$emit('question', this.selected.question)
            }
        },
        methods: {
            setMergeState() {
                this.$emit('merge', this.merged)
                this.merged = this.merged !== true
            }
        },
        watch: {
            choices: function () {
                this.selected.question = this.choices[0].value
                this.$emit('question', this.selected.question)
            }
        }
    }
</script>

<style scoped>

</style>
