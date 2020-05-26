<template>
    <div>
        <p class="poll-mode-indicator">
            <i>
                Poll for pseudonymized participants.
            </i>
        </p>

        <p>
            <SelectStatus></SelectStatus>
        </p>

        <p>
            <ManageParticipants></ManageParticipants>
        </p>

        <p>
            <DeletePoll></DeletePoll>
        </p>
    </div>
</template>

<script>
    import SelectStatus from "./SelectStatus";
    import ManageParticipants from "./ManageParticipants";
    import DeletePoll from "./DeletePoll";

    export default {
        name: "ConfigurePoll",
        components: { SelectStatus, ManageParticipants, DeletePoll }
    }
</script>

<style scoped>
    .poll-mode-indicator {

    }
</style>
