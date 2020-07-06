<template>
    <b-card>

        <h6>Download poll or entries</h6>

        <div v-if="isMobile()">

            <b-container>
                <b-row style="margin-bottom: 2vh">
                    <b-dropdown text="Download poll">
                        <b-dropdown-item @click="downloadFromStore('poll', 'txt')">
                            Download Poll as txt file
                        </b-dropdown-item>
                        <b-dropdown-item @click="downloadFromStore('poll', 'json')">
                            Download Poll as json file
                        </b-dropdown-item>
                    </b-dropdown>
                </b-row>
                <b-row>
                    <b-button @click="downloadFromStore('entries', 'json')"
                    >Download Poll entries as json file</b-button>
                </b-row>
            </b-container>
        </div>
        <div v-if="!isMobile()" class="float-left">

            <b-dropdown text="Download poll">
                <b-dropdown-item @click="downloadFromStore('poll', 'txt')">
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
            isMobile() {
                if (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)) {
                    return true
                } else {
                    return false
                }
            },
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
