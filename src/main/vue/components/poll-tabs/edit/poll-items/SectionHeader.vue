<template>
    <div>
        <span class="text-muted">{{ model.description }}</span>
    </div>
</template>

<script>
    export default {
        name: "SectionHeader",
        props: {
            model: {
                type: Object,
                required: true
            }
        }
    }
</script>

<style scoped>
</style>
