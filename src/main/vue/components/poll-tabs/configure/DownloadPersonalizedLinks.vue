<template>
    <div>
        <b-button
            @click="downloadFromStore('participants', 'csv')">
            Download Personalized Links
        </b-button>


    </div>

</template>

<script>
    import {mapState} from "vuex";

    export default {
        name: "DownloadPersonalizedLinks",
        methods: {
            downloadFromStore(type, format) {

                let cmd = {
                    id: "",
                    type: type,
                    format: format
                };

                this.download(cmd);
            }

        },
        computed: {
            ...mapState('currentPoll', {
                poll: 'poll'}),
            ...mapState('participants', {
                participants: 'participants'
            })
        },
    }
</script>

<style scoped>

</style>
