<template>
    <b-container style="margin-top: 35vh;">
        <b-row align-h="center">
            <b-card
                border-variant="danger"
                header=" "
                header-bg-variant="danger"
                title="Error sending your answers."
                style="width: 360px">
                <p>
                    Note that if you already participated, you cannot participate again.
                </p>
            </b-card>
        </b-row>
    </b-container>
</template>

<script>
    export default {
        name: "PollResponseError"
    }
</script>

<style scoped>

</style>
