<template>
    <div>
        <p>
            <Anonymity></Anonymity>
        </p>

        <p>
            <Iterations/>
        </p>

        <p>
            <Launch></Launch>
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
            <Download></Download>
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
    import Download from "./Download";
    import Launch from "./Launch";
    import Iterations from "./Iterations";

    export default {
        name: "ConfigurePoll",
        components: {
            Iterations,
            Launch, Download, Design, Anonymity, SelectStatus, ManageParticipants, DeletePoll
        }
    }

</script>

<style scoped>
</style>
