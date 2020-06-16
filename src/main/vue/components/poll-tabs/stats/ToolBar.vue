<template>
    <div>
        <b-button-toolbar v-if="actives !== undefined">

            <b-form-group>
                <!-- && choices !== undefined-->
                <b-form-select
                    v-if="actives[4] === true"
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
                    v-if="actives[0] === true"
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
                        v-if="actives[1] === true"
                        title="bar-chart">
                        <b-icon icon="bar-chart-fill"></b-icon>
                    </b-button>
                    <b-button
                        variant="outline-secondary"
                        v-on:click="$emit('chart', 'donut')"
                        v-if="actives[2] === true"
                        title="donut-chart">
                        <b-icon icon="pie-chart-fill"></b-icon>
                    </b-button>
                    <b-button
                        variant="outline-secondary"
                        v-on:click="$emit('chart', 'boxplot')"
                        v-if="actives[3] === true"
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
                    v-if="actives[5] === true"
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
                    v-if="actives[6] === true"
                    title="close comparision">
                    <b-icon icon="trash"></b-icon>
                </b-button>
            </b-form-group>

        </b-button-toolbar>
    </div>
</template>

<script>
    export default {
        name: "ToolBar",
        // actives: dataChange, bar, donut, boxplot, question, merge, delete
        props: ['actives','choices'],
        data() {
            return {
                selected: {
                    question: '',
                    frequency: ''
                },
                merged: true,
            }
        },
        methods: {
            setMergeState() {
                this.$emit('merge', this.merged)
                this.merged = this.merged !== true
            }
        }
    }
</script>

<style scoped>

</style>
