<template>
    <div>
        <span class="text-muted">{{ model.description }}</span>
    </div>
</template>

<script>
    import SectionHeaderModel from "../../../../store/poll-item-models/SectionHeaderModel";

    export default {
        name: "SectionHeader",
        props: {
            model: {
                type: SectionHeaderModel,
                required: true
            }
        }
    }
</script>

<style scoped>
</style>
