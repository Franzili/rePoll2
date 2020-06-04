<template>
    <ul class="palette-list">
        <li v-for="itemModel in pollItemModels" v-bind:key="itemModel.index">
            <PaletteItem v-bind:itemModel="itemModel"></PaletteItem>
        </li>
    </ul>
</template>

<script>
    import { models } from "../../../store/poll-item-models";
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

    .palette-list > :first-child {
        margin-bottom: 10px;
    }
</style>
