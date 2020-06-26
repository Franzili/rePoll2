<template>
    <div>
        <p>
            {{item.questions}}
        </p>
        <ToolBar v-on:edit="showModal(item.questions)"
                 v-bind:e-size="'sm'"
                 v-bind:actives="actives"></ToolBar>
        <CheckModal ref="mymodal"></CheckModal>
    </div>
</template>

<script>
    import CheckModal from "../stats/utils/CheckModal";
    import ToolBar from "../stats/utils/ToolBar";
    export default {
        name: "ConsistGroup",
        props: ['item'],
        data () {
            return {
                actives: [null,null,null,null,null,null,true,true],
            }
        },
        methods: {
            showModal(list) {
                this.$refs.mymodal.show(list)
            },
        },
        components: {
            CheckModal,
            ToolBar
        }
    }
</script>

<style scoped>

</style>
