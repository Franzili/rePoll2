<template>
    <ul class="poll-main-view">
        <PollItem v-for="item in pollStructure" v-bind:key="item.abc" :model="item"></PollItem>
    </ul>
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

<style lang="scss" scoped>
    .poll-main-view {
        // turn off default list styling
        list-style-type: none;
        padding-left: 0;
    }
</style>
