<template>
    <b-row class="poll-editor">
        <b-col cols="2" class="sidebar">
            <div class="sticky-top sticky-offset">
                <QuestionPalette></QuestionPalette>
            </div>
        </b-col>

        <b-col cols="7" class="editor-column">
            <PollMainView></PollMainView>
        </b-col>

        <b-col cols="3" class="sidebar">
            <div class="sticky-top sticky-offset">
                Structure
            </div>
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
    @import "../../../assets/stylesheet.scss";

    .poll-editor {
        margin-top: 10px;
    }

    .editor-column {
        border-left: 1px solid $border-color;
        border-right: 1px solid $border-color;
    }

    .sidebar {
        background-color: $light;
    }

    .sticky-offset {
        top: 100px;
    }
</style>
