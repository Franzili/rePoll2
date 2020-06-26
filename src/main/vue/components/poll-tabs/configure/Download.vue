<template>
    <b-card>

        <h6>Download poll or entries</h6>

        <div class="float-left">

            <b-button @click="downloadFromStore('human')"
            >Download human readable Poll as txt file</b-button>

            <b-button class="ml-3"
                      @click="downloadFromStore('json')"
            >Download Poll as json file</b-button>

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

            downloadFromStore(format) {

                let cmd = {
                    id: "36808e60-39f7-41f0-b991-f08b80fe8226",
                    type: 'poll',
                    format: format
                };

                this.download(cmd);

                /*axios({url: '/api/v1/download/' + "36808e60-39f7-41f0-b991-f08b80fe8226" + '/poll/human/',
                method: 'GET',
                responseType: 'blob'}).then((response) => {
                    let fileURL = window.URL.createObjectURL(new Blob([response.data]));
                    let fileLink = document.createElement('a');

                    fileLink.href = fileURL;
                    fileLink.setAttribute('download', 'testfile.txt');
                    document.body.appendChild(fileLink);

                    fileLink.click();
                })
                 */
            }
        }
    }
</script>

<style scoped>

</style>
