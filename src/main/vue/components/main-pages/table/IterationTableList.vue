<template>

<div>
    <div v-bind:key="iteration.id" v-for="iteration in iterations">
        <b-container>
            <b-row>
                <b-col>
                    <p v-if="iteration.status === 'OPEN'">
                        <IterationTableElementOPEN v-bind:iteration="iteration"/>
                    </p>
                </b-col>
            </b-row>
        </b-container>

        <b-container>
            <b-row>
                <b-col>
                    <p v-if="iteration.status === 'OPEN'">
                        <IterationTableElementSCHEDULED v-bind:iteration="iteration"/>
                    </p>
                </b-col>
            </b-row>
        </b-container>

        <b-container>
            <b-row>
                <b-col>
                    <p v-if="iteration.status === 'CLOSED'">
                        <IterationTableElementCLOSED v-bind:iteration="iteration"/>
                    </p>
                </b-col>
            </b-row>
        </b-container>
    </div>
</div>

</template>

<script>

    import IterationTableElementOPEN from "./IterationTableElementOPEN";
    import IterationTableElementSCHEDULED from "./IterationTableElementSCHEDULED";
    import IterationTableElementCLOSED from "./IterationTableElementCLOSED";

    export default {
        name: "IterationTableList",
        props: ["iterations"],

        components: {IterationTableElementOPEN, IterationTableElementSCHEDULED, IterationTableElementCLOSED}
    }
</script>

<style scoped>

</style>
