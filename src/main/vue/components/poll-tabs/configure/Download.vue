<template>
    <b-card>

        <h6>Download poll or entries</h6>

        <div class="float-left">

            <b-dropdown text="Download poll">
                <b-dropdown-item @click="downloadFromStore('poll', 'human')">
                    Download Poll as txt file
                </b-dropdown-item>
                <b-dropdown-item @click="downloadFromStore('poll', 'json')">
                    Download Poll as json file
                </b-dropdown-item>
            </b-dropdown>

            <b-button class="ml-3"
                      @click="downloadFromStore('entries', 'json')"
            >Download Poll entries as json file</b-button>
        </div>
    </b-card>
</template>

<script>
    import {mapActions} from "vuex";

    export default {
        name: "Download",
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
