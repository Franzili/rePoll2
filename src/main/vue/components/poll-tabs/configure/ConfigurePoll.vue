<template>
    <div>
        <p>
            <Anonymity></Anonymity>
        </p>

        <p v-if="poll.status === 'EDITING'">
            <Launch></Launch>
        </p>

        <p v-else-if="poll.status === 'LAUNCHED'">
            <Iterations/>
        </p>

        <p>
            <ManageParticipants></ManageParticipants>
        </p>

        <p v-if="poll.status !== 'LAUNCHED'">
            <Design></Design>
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
    import ManageParticipants from "./ManageParticipants";
    import DeletePoll from "./DeletePoll";
    import Anonymity from "./Anonymity";
    import Design from "./Design";
    import Download from "./Download";
    import Launch from "./Launch";
    import Iterations from "./Iterations";

    import {mapState} from "vuex"

    export default {
        name: "ConfigurePoll",
        components: {
            Iterations,
            Launch,
            Download,
            Design,
            Anonymity,
            ManageParticipants,
            DeletePoll
        },
        computed: {
            ...mapState("currentPoll", {
                poll: "poll"
            })
        }
    }

</script>

<style scoped>
</style>
