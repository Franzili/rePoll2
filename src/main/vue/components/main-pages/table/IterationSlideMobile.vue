<template>
    <b-container class="text-muted; slide-style">
        <b-row>
            <span v-if="current !== null">Current Iteration:</span>
            <span v-else>Last Iteration:</span>
        </b-row>
        <b-row>
            <strong>Start:</strong>
        </b-row>
        <b-row>
            <span>{{(dateTimeFormat.format(new Date(iterationData.start)))}}</span>
        </b-row>
        <b-row>
            <strong>End:</strong>
        </b-row>
        <b-row>
            <span v-if="iterationData.end !== null">
                {{(dateTimeFormat.format(new Date(iterationData.end)))}}
            </span>
            <span v-else>
                Open End
            </span>
        </b-row>
        <b-row>
            <span><strong>Participants:</strong>&nbsp;&nbsp;{{iterationData.pollEntries}}</span>
        </b-row>
    </b-container>
</template>

<script>
    export default {
        name: "IterationSlideMobile",
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
