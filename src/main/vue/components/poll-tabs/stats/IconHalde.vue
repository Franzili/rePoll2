<template>
    <b-container>
        <b-row>
            <p>
                <b-iconstack font-scale="5">
                    <b-icon stacked shift-h="10" icon="dash"></b-icon>
                    <b-icon stacked shift-h="" icon="vr"></b-icon>
                    <b-icon stacked shift-h="-10" icon="dash"></b-icon>
                </b-iconstack>
            </p>

            <p>
                <b-iconstack shift-h="-3.5" font-scale="5">
                    <b-icon stacked shift-h="5" icon="box-arrow-right"></b-icon>
                    <b-icon stacked icon="box-arrow-left"></b-icon>
                </b-iconstack>
            </p>
        </b-row>
    </b-container>
</template>

<script>
    export default {
        name: "IconHalde"
    }
</script>

<style scoped>

</style>
