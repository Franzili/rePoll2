<template>
    <div>
        <SectionHeader v-if="model.type === 'SectionHeaderModel'" v-bind:model="model"></SectionHeader>
        <TextQuestion v-else-if="model.type === 'TextQuestionModel'" v-bind:model="model"></TextQuestion>
    </div>
</template>

<script>
    import PollItemModel from "../../../../store/poll-item-models/PollItemModel";

    import SectionHeader from "./SectionHeader";
    import TextQuestion from "./TextQuestion";

    export default {
        name: "PollItem",
        components: {SectionHeader, TextQuestion},
        props: {
            model: {
                type: PollItemModel,
                required: true
            }
        }
    }
</script>

<style scoped>

</style>
