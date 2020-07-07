<template>
    <b-row class="text-muted; slide-style">
        <b-col cols="2">
            <span v-if="current !== null">Current Iteration:</span>
            <span v-else>Last Iteration:</span>
        </b-col>
        <b-col cols="1">
            <strong>Start:</strong>
        </b-col>
        <b-col cols="3">
            <span>{{(dateTimeFormat.format(new Date(iterationData.start)))}}</span>
        </b-col>
        <b-col cols="1">
            <strong>End:</strong>
        </b-col>
        <b-col cols="3">
            <span v-if="iterationData.end !== null">
                {{(dateTimeFormat.format(new Date(iterationData.end)))}}
            </span>
            <span v-else>
                Open End
            </span>
        </b-col>
        <b-col cols="2">
            <span><strong>Participants:</strong>&nbsp;&nbsp;{{iterationData.pollEntries}}</span>
        </b-col>
    </b-row>
</template>

<script>
    export default {
        name: "IterationSlide",
        props: ['iterationData', 'current'],
        data() {
            return {
                dateTimeFormat: new Intl.DateTimeFormat('en', {
                    year: 'numeric',
                    month: 'short',
                    day: '2-digit',
                    weekday: 'short',
                    hour: 'numeric',
                    minute: 'numeric',
                })
            }
        },
    }
</script>

<style scoped>
    .slide-style {
        background-color: white;
        margin-top: 1rem;
        border: 2px solid #3eab37;
    }
</style>
