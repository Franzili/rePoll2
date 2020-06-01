<template>
    <span>
        <b-icon-pen v-if="questionType.name === 'TextQuestionModel'"></b-icon-pen>
        {{ questionType.getDisplayType() }}
    </span>
</template>

<script>
    export default {
        name: "PaletteItem",
        props: {
            questionType: {
                required: true,
                type: Function
            }
        },
    }
</script>

<style scoped>
    .descriptiveLocalStyleClass {

    }

</style>
