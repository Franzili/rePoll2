<template>
    <b-row>
        <div>
            <h2>{{ model.title }}</h2>
            <p class="text-muted">{{ model.description }}</p>
        </div>
    </b-row>
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
