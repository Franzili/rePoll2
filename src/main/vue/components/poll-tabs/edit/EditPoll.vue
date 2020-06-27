<template>
    <!--
        Poll Editor main component.
    -->
    <b-row class="poll-editor">
        <b-col cols="1" lg="2" class="sidebar">
            <div class="sticky-offset sticky-top">
                <QuestionPalette></QuestionPalette>
            </div>
            <!-- <Upload></Upload> -->
        </b-col>

        <b-col class="editor-column">
            <PollMainView></PollMainView>
        </b-col>

        <b-col cols="3" class="sidebar">
            <div class="sticky-offset sticky-top">
                <Outline :poll-structure="pollStructure"></Outline>
            </div>
        </b-col>
    </b-row>
</template>

<script>
    import {mapGetters} from "vuex";
    import QuestionPalette from "./QuestionPalette";
    import PollMainView from "./PollMainView";
    import Outline from "../../Outline";
    export default {
        name: "EditPoll",
        computed: {
            ...mapGetters('currentPoll', {
                pollStructure: 'pollStructureFlat'
            })
        },
        components: {PollMainView, QuestionPalette, Outline},
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
        padding-top: 10px;
    }

    .sticky-offset {
        top: 150px;
    }
</style>
