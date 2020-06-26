<template>
    <b-card
        border-variant="primary"
        align="left"
        bg-variant="light"
    >
        <b-row>
            <b-col cols="8">
                {{iteration.id}}
                <p class="status">{{iteration.status}}</p>
            </b-col>

            <b-col
                align-self="center">
                <p v-show="iteration.status === 'CLOSED'"
                ><span class="start">Opened: </span>{{iteration.start}}</p>
                <p v-show="iteration.status === 'CLOSED'"
                ><span class="start">Closed: </span>{{iteration.end}}</p>
            </b-col>
            <b-col cols="4" style="text-align: center">

                <!-- change with link to charts -->
                <!--
                <router-link class="configLink"
                             :to="{ name: 'answer', params: {id: poll.id}}"
                >View Results</router-link>
                -->

            </b-col>
        </b-row>

    </b-card>
</template>

<script>
    export default {
        name: "IterationTableElementCLOSED",
        props: ["iteration"],
        methods: {
        }
    }
</script>

<style scoped>

</style>
