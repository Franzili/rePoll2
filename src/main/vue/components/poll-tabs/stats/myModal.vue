<template>
    <b-modal ref="modal" v-if="showModal">
        <div class="d-block text-center">
            <h3>Hello From My Modal!</h3>
        </div>
    </b-modal>
</template>

<script>
    export default {
        name: "myModal",
        data() {
            return {
                showModal: true
            }
        },
        methods: {
            show() {
                this.$refs.modal.show()
            },
            hide() {
                this.showModal = false
            }
        }
    }
</script>

<style scoped>

</style>
