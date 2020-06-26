<template>


    <!--
<div>
    <div v-bind:key="iteration.id" v-for="iteration in iterations">
        <!-
        container,row und col weg.
        div verwenden oder
        suchen nach "list group" !!!!!! das beste hierfür
        ->
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
                    <p v-if="iteration.status === 'SCHEDULED'">
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
    -->


    <div class="row">
        <div v-bind:key="iteration.id" v-for="iteration in iterations">
            <div class="list-group" id="IterationSCHEDULED" role="tablist">
                <p v-if="iteration.status === 'SCHEDULED'">
                    <IterationTableElementSCHEDULED v-bind:iteration="iteration"/>
                </p>
            </div>
            <div class="list-group" id="IterationOPEN" role="tablist">
                <p v-if="iteration.status === 'OPEN'">
                    <IterationTableElementOPEN v-bind:iteration="iteration"/>
                </p>
            </div>
            <div class="list-group" id="IterationCLOSED" role="tablist">
                <p v-if="iteration.status === 'CLOSED'">
                    <IterationTableElementCLOSED v-bind:iteration="iteration"/>
                </p>
            </div>
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
