<template>
    <div>
        <PollItem v-for="item in pollStructure" v-bind:key="item.abc" :model="item"></PollItem>
    </div>
</template>

<script>
    import PollItem from "./poll-items/PollItem";
    import {mapGetters} from "vuex"
    export default {
        name: "PollMainView",
        computed: {
            ...mapGetters('currentPoll', {
                pollStructure: 'pollStructureFlat'
            })
        },
        components: { PollItem }

    }
</script>

<style scoped>

</style>
