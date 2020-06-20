<template>
    <div>
        <p>
            <Anonymity></Anonymity>
        </p>
        <p>
            <SelectStatus></SelectStatus>
        </p>
        <p>
            <Design></Design>
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
    import Anonymity from "./Anonymity";
    import Design from "./Design";

    export default {
        name: "ConfigurePoll",
        components: {Design, Anonymity, SelectStatus, ManageParticipants, DeletePoll }
    }
</script>

<style scoped>
</style>
