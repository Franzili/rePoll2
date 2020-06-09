<template>
    <draggable tag="ul"
               class="palette-list"
               :list="pollItemModels"
               :group="{ name: 'pollEditor', pull: 'clone', put: false }"
               :clone="createItem">
        <li v-for="itemModel in pollItemModels" v-bind:key="itemModel.index">
            <PaletteItem v-bind:itemModel="itemModel"></PaletteItem>
        </li>
    </draggable>
</template>

<script>
    import { models } from "../../../store/poll-item-models/index"

    import PaletteItem from "./PaletteItem";

    import draggable from "vuedraggable";
    export default {

        name: "QuestionPalette",
        props: [],
        data() {
            return {
                pollItemModels: models
            }
        },

        methods: {
            createItem(modelConstructor) {
                console.log(modelConstructor);
                let a = new modelConstructor(420, "abcdefg");
                console.log("a:  ")
                console.log(a)
                return a;
            }
        },

        components: {
            PaletteItem,
            draggable
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
