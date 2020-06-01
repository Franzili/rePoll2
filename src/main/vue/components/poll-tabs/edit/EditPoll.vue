<template>
    <b-row class="poll-editor">
        <b-col cols="3">
            <QuestionPalette></QuestionPalette>
        </b-col>

        <b-col cols="6">
            <PollMainView></PollMainView>
        </b-col>

        <b-col cols="3">
            Structure
        </b-col>
    </b-row>
</template>

<script>
    import {mapGetters} from "vuex";
    import QuestionPalette from "./QuestionPalette";
    import PollMainView from "./PollMainView";
    export default {
        name: "EditPoll",
        computed: {
            ...mapGetters('currentPoll', {
                pollStructure: 'pollStructureFlat'
            })
        },
        components: {PollMainView, QuestionPalette},
    }
</script>

<style scoped lang="scss">
    .poll-editor {
        margin-top: 10px;
    }
</style>
