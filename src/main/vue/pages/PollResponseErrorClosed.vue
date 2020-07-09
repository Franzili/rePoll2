<template>
    <b-container style="margin-top: 35vh;">
        <b-row align-h="center">
            <b-card
                border-variant="danger"
                header=" "
                header-bg-variant="danger"
                title="Poll is closed"
                style="width: 360px">
                <p>
                    This poll is closed at the moment. If you think that this is an error, contact
                    the administrator.
                </p>
            </b-card>
        </b-row>
    </b-container>
</template>

<script>
    export default {
        name: "PollResponseErrorClosed"
    }
</script>

<style scoped>

</style>
