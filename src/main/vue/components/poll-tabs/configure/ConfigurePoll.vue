<template>
    <div>
        <p>
            <Anonymity></Anonymity>
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

        <p>
            <Download></Download>
        </p>
    </div>
</template>

<script>
    import SelectStatus from "./SelectStatus";
    import ManageParticipants from "./ManageParticipants";
    import DeletePoll from "./DeletePoll";
    import Anonymity from "./Anonymity";
    import Download from "./Download";

    export default {
        name: "ConfigurePoll",
        components: {Download, Anonymity, SelectStatus, ManageParticipants, DeletePoll }
    }
</script>

<style scoped>
</style>
