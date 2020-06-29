<template>


    <div>
        <div v-bind:key="iteration.id" v-for="iteration in iterations">
            <div class="list-group" id="IterationOPEN" role="tablist">
                <p v-if="iteration.status === 'OPEN'">
                    <IterationTableElementOPEN v-bind:iteration="iteration"/>
                </p>
            </div>

            <div class="list-group" id="IterationSCHEDULED" role="tablist">
                <p v-if="iteration.status === 'SCHEDULED'">
                    <IterationTableElementSCHEDULED v-bind:iteration="iteration"/>
                </p>
            </div>

            <div class="list-group" id="IterationCLOSED" role="tablist">
                <p v-if="iteration.status === 'CLOSED'">
                    <IterationTableElementCLOSED v-bind:iteration="iteration"/>
                </p>
            </div>
        </div>
    </div>

    <!--
        <b-container>
            <b-row v-bind:key="iteration.id" v-for="iteration in iterations">
                <b-col>
                    <p v-if="iteration.status === 'OPEN'">
                        <IterationTableElementOPEN v-bind:iteration="iteration"/>
                    </p>
                </b-col>
                <b-col>
                    <p v-if="iteration.status === 'SCHEDULED'">
                        <IterationTableElementSCHEDULED v-bind:iteration="iteration"/>
                    </p>
                </b-col>
                <b-col>
                    <p v-if="iteration.status === 'CLOSED'">
                        <IterationTableElementCLOSED v-bind:iteration="iteration"/>
                    </p>
                </b-col>
            </b-row>
        </b-container>
    -->

</template>

<script>
    import IterationTableElementSCHEDULED from "../../main-pages/table/IterationTableElementSCHEDULED";
    import IterationTableElementOPEN from "../../main-pages/table/IterationTableElementOPEN";
    import IterationTableElementCLOSED from "../../main-pages/table/IterationTableElementCLOSED";

    export default {
        name: "IterationTableList",
        props: ["iterations"],
        components: {IterationTableElementSCHEDULED,IterationTableElementOPEN, IterationTableElementCLOSED}
    }
</script>

<style scoped>
</style>
