<template>

    <b-container>

        <b-row v-bind:key="iteration.id"  v-for="iteration in iterations">
            <b-col>
                <p v-if="iteration.status === 'CLOSED'">
                    <IterationTableElementCLOSED v-bind:iteration="iteration"/>
                </p>
            </b-col>
        </b-row>
    </b-container>


</template>

<script>

    import IterationTableElementCLOSED from "./IterationTableElementCLOSED";

    export default {
        name: "IterationTableListCLOSED",
        props: ["iterations"],

        components: {
            IterationTableElementCLOSED
        }
    }
</script>

<style scoped>

</style>
