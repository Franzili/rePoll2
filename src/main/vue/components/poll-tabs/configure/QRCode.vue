<template>
    <div
        style="align-items: center"
    >
        <!-- ToDo: Make this responsive to display size -->
        <qrcode-vue :value="link" :size="qrsize" level="H"></qrcode-vue>
    </div>
</template>

<script>
    import QrcodeVue from "qrcode.vue";

    export default {
        name: "QRCode",
        props: [ 'link' ],
        components: {
            QrcodeVue
        },
        data() {
            return {
                qrsize: 450
            }
        }
    }
</script>

<style scoped>

</style>
