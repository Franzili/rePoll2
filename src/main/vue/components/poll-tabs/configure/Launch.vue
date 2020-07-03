<template>
    <b-card>
        <h6>Launch</h6>
        <div class="d-flex align-items-center">
            <span class="text-muted flex-grow-1">
                If you launch the poll, you will not be able to edit it again.
            </span>
            <b-button class="button-spacing">
                Schedule
            </b-button>
            <b-button variant="primary" class="button-spacing">
                Launch now!
            </b-button>
        </div>
    </b-card>
</template>

<script>
    export default {
        name: "Launch.vue"
    }
</script>

<style scoped>
    .button-spacing {
        margin-left: 5px;
    }

</style>
