<template>
    <ul class="palette-list">
        <li v-for="itemModel in pollItemModels" v-bind:key="itemModel.index">
            <PaletteItem v-bind:itemModel="itemModel"></PaletteItem>
        </li>
    </ul>
</template>

<script>
    import { models } from "../../../store/poll-item-models/index"

    import PaletteItem from "./PaletteItem";

    //import draggable from "vuedraggable";
    export default {

        name: "QuestionPalette",
        props: [],
        data() {
            return {
                pollItemModels: models
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

    // first child is section header
    .palette-list > :first-child {
        margin-bottom: 10px;
    }
</style>
