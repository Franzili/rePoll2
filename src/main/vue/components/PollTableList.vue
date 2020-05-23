<template>
    <div>
        <div v-bind:key="poll.id" v-for="poll in polls">
            <PollTableElement v-bind:poll="poll"/>
        </div>
    </div>
</template>

<script>
    import PollTableElement from "./PollTableElement";

    export default {
        name: "PollTableList",
        components: {
            PollTableElement
        },
        props: ["polls"]
    }
</script>

<style scoped>

</style>
