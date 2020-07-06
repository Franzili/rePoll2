<template>
    <div>
        <b-button
            @click="downloadFromStore('participants', 'csv')">
            Download Personalized Links
        </b-button>


    </div>

</template>

<script>
    import {mapActions} from "vuex";

    export default {
        name: "DownloadPersonalizedLinks",
        methods: {
            ...mapActions('currentPoll', {
                download: 'download'
            }),

            downloadFromStore(type, format) {

                let cmd = {
                    id: "",
                    type: type,
                    format: format
                };

                this.download(cmd);
            }

        }
    }
</script>

<style scoped>

</style>
