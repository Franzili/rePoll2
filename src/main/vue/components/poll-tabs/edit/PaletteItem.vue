<template>
    <!--
        An Item to be shown in the questions palette.
    -->
    <div class="w-100 palette-item text-left">
        <b-icon-type-h1 v-if="itemModel.name === 'SectionHeaderModel'"></b-icon-type-h1>
        <b-icon-pen v-if="itemModel.name === 'TextQuestionModel'"></b-icon-pen>
        <b-icon-check v-else-if="itemModel.name === 'SingleChoiceQuestionModel'"></b-icon-check>
        <b-icon-check-all v-else-if="itemModel.name === 'MultiChoiceQuestionModel'"></b-icon-check-all>
        <b-icon-arrow-left-right v-else-if="itemModel.name === 'ScaleQuestionModel'"></b-icon-arrow-left-right>
        <small class="palette-item-label">{{ itemModel.getDisplayType() }}</small>
    </div>
</template>

<script>
    export default {
        name: "PaletteItem",
        props: {
            itemModel: {
                required: true,
                type: Function
            }
        },
    }
</script>

<style lang="scss" scoped>
    @import "../../../assets/stylesheet.scss";

    .palette-item {
        display: flex;

        background-color: $secondary;
        color: $white;
        cursor: default;

        font-size: 1.2em;

        margin-bottom: 5px;
        margin-top: 5px;
        padding: 0.5rem 1rem;
        border-radius: $border-radius;

        align-items: center;
    }

    .palette-item-label {
        margin-left: 10px;

        flex-grow: 1;
    }

    @media (max-width: $md) {
        .palette-item-label {
            display: none;
        }
    }

</style>
