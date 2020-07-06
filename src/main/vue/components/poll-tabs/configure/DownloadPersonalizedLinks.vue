<template>
    <div>
        <b-button
            v-if="poll.anonymity === 'NON_ANONYMOUS'"
            @click="downloadFromStore('participants', 'csv')">
            Download Personalized Links
        </b-button>


    </div>

</template>

<script>
    import {mapActions, mapState} from "vuex";

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

        },
        computed: {
            ...mapState('currentPoll', {
                poll: 'poll',
            }),
        }
    }
</script>

<style scoped>

</style>
