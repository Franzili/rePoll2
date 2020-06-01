<template>
    <div>

    </div>
</template>

<script>
    import PollItemModel from "../../../store/poll-item-models/PollItemModel";

    export default {
        name: "PollItem",
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
