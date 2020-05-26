<template>
    <div>
        <div v-bind:key="poll.id" v-for="poll in polls">
            <AnswerPollTableElement v-bind:poll="poll"/>
        </div>
    </div>
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
