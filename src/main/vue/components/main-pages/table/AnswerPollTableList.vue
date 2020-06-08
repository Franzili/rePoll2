<template>

    <b-container>
        <b-row v-bind:key="poll.id" v-for="poll in polls">
            <b-col>
                <p>
                    <AnswerPollTableElement v-bind:poll="poll"/>
                </p>
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
    import AnswerPollTableElement from "./AnswerPollTableElement";

    export default {
        name: "AnswerPollTableList",
        props: ["polls"],
        components: {
            AnswerPollTableElement
        }
    }
</script>

<style scoped>

</style>
