<template>
    <ul class="palette-list">
        <li v-for="questionType in questionTypes" v-bind:key="questionType.index">
            <PaletteItem v-bind:question-type="questionType"></PaletteItem>
        </li>
    </ul>
</template>

<script>
    import { questionTypes} from "../../../store/poll-item-models";
    import PaletteItem from "./PaletteItem";

    //import draggable from "vuedraggable";
    export default {

        // Order is related to its use
        name: "QuestionPalette",
        // passed variables by e.g v-bind
        props: [],
        // variables provided by this component
        data() {
            return {
                questionTypes: questionTypes
            }
        },

        // after import, components need to be listed here as well
        components: {
            PaletteItem
            //draggable
        }
    }
</script>

<style scoped lang="scss">
    .palette-list {
        list-style-type: none;
        padding: 0;
    }
</style>
