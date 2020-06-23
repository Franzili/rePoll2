<template>

    <b-container>

        <b-row v-bind:key="iteration.id" v-for="iteration in iterations">
            <b-col>
                <p>
                    <IterationTableElementCLOSED v-bind:iteration="iteration"/>
                </p>
            </b-col>
        </b-row>
    </b-container>


</template>

<script>

    import IterationTableElementCLOSED from "./IterationTableElementCLOSED";

    export default {
        name: "IterationTableList",
        props: ["iterations"],

        components: {
            IterationTableElementCLOSED
        }
    }
</script>

<style scoped>

</style>
