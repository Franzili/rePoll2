<template>
    <!--
        Poll Editor main component.
    -->
    <b-row :style="'background-color:' + poll.design.backgroundColour"
           class="poll-editor">
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
                <b-row style="min-height: 40vh; max-height: 40vh; overflow-y: auto;">
                    <Outline :poll-structure="pollStructure"></Outline>
                </b-row>
                <ConsistEdit></ConsistEdit>
            </div>
        </b-col>
    </b-row>
</template>

<script>
    import {mapGetters, mapState} from "vuex";
    import QuestionPalette from "./QuestionPalette";
    import PollMainView from "./PollMainView";
    import Outline from "../../Outline";
    import ConsistEdit from "./ConsistEdit";
    export default {
        name: "EditPoll",
        computed: {
            ...mapGetters('currentPoll', {
                pollStructure: 'pollStructureFlat'
            }),
            ...mapState('currentPoll', {
                poll: 'poll',
            })
        },
        components: {
            ConsistEdit,
            PollMainView,
            QuestionPalette,
            Outline},
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
        min-height: calc(100vh - 150px);
        background-color: $light;
        padding-top: 10px;
    }

    .sticky-offset {
        top: 150px;
    }
</style>
