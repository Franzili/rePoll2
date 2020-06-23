<template>
    <b-card>
        <b-button variant="primary"
                  class="float-right"
                  @click="download"
        >Download Poll as txt file</b-button>
    </b-card>
</template>

<script>
    import axios from "axios";

    export default {
        name: "Download",
        methods: {
            download() {
                axios({url: '/api/v1/download/' + "36808e60-39f7-41f0-b991-f08b80fe8226" + '/poll/human/',
                method: 'GET',
                responseType: 'blob'}).then((response) => {
                    let fileURL = window.URL.createObjectURL(new Blob([response.data]));
                    let fileLink = document.createElement('a');

                    fileLink.href = fileURL;
                    fileLink.setAttribute('download', 'testfile.txt');
                    document.body.appendChild(fileLink);

                    fileLink.click();
                })
            }
        }
    }
</script>

<style scoped>

</style>
