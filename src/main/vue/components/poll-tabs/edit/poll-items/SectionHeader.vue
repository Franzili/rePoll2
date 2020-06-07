<template>
    <b-card class="header-card">
        <h2>{{ model.title }}</h2>
        <span class="text-muted">{{ model.description }}</span>
    </b-card>
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
    .header-card {
        margin-top: 20px;
        border: none;
    }
</style>
