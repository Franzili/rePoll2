<template>
    <b-container>

        <b-button @click="showModal" > lalaa</b-button>
        <myModal ref="mymodal" v-bind:modalObj="getPollStructure"></myModal>

        <p>
            {{getPollStructure}}
        </p>
    </b-container>
</template>

<script>
    import {mapGetters, mapState} from "vuex";
    import myModal from "./myModal";

    export default {
        name: "Compare",
        computed: {
            ...mapState('currentPoll', {
                statistics: 'statistics'
            }),
            ...mapGetters('currentPoll', {
                getPollStructure: 'statStructureObj'
            })
        },
        methods: {
            showModal() {
                this.$refs.mymodal.show()
            },
        },
        components: {
            myModal
        }
    }
</script>

<style scoped>

</style>
