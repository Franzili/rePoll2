<template>
    <ul class="poll-main-view">
        <PollItem v-for="item in pollStructure" v-bind:key="item.id" :model="item" :id="'pollItem-' + item.id"></PollItem>
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
