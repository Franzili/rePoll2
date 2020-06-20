<template>
    <div>
        <p>
            <Anonymity></Anonymity>
        </p>
        <p>
            <SelectStatus></SelectStatus>
        </p>

        <p>
            <Iteration></Iteration>
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
    import Iteration from "./Iteration";

    export default {
        name: "ConfigurePoll",
        components: {Anonymity, SelectStatus, ManageParticipants, DeletePoll, Iteration }
    }
</script>

<style scoped>
</style>
