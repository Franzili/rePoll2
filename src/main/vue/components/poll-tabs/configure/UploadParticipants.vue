<template>

    <div>

        <b-form-file
            type="file"
            id="selectFile"
            accept=".csv"
            style="margin-bottom: 2vh; margin-top: 3vh"
            no-drop
            placeholder="Select a .csv-file to import"
        ></b-form-file>


        <!--<input type="file"
               data-toggle="tooltip"
               title="Select a .csv-file to import"
               data-placement="auto"
               id="selectFile"
               value="Import"
               accept=".csv"
               style="margin-bottom: 2vh"/><br />-->
        <b-button data-toggle="tooltip"
                  title="Import data"
                  @click="handleFiles">Import File</b-button>
        <div class="output"></div>


    </div>



</template>

<script>


    import {mapActions, mapState} from "vuex";
    export default {
        name: "UploadParticipants",
        data() {
            return {
                newParticipant: '',
                participant: [],
            }
        },
        computed: {
            ...mapState('participants', {mailAnswer: 'mailAnswer'})
        },
        methods: {
            ...mapActions('participants', {create: "create"}),
            async addParticipant(participantCmd) {
                await this.create(participantCmd)
                this.makeToast(this.mailAnswer)
            },
            makeToast(message) {
                this.$bvToast.toast(message, {
                    title: 'Mail',
                    autoHideDelay: 10000,
                    appendToast: false
                })
            },
            async handleFiles() {
                const input = document.getElementById('selectFile').files;
                if (input[0] != null) {
                    const reader = new FileReader();
                    const csv = input[0];
                    var newParticipant;
                    reader.onload = e => {
                        document.querySelector('.output').innerText = e.target.result;
                        newParticipant = e.target.result;
                    };
                    reader.readAsText(csv);
                    setTimeout(() => {
                        this.newParticipant = newParticipant;
                        var res = this.newParticipant.split("\n");
                        for (var i = 0; i < res.length - 1; i++) {
                            this.participant = res[i];
                            if(res[i].length === 2) {
                                var tmp = this.participant.split(",");
                                let participantCmd = {
                                    fullName: tmp[0],
                                    email: tmp[1],
                                };
                                this.addParticipant(participantCmd);
                            } else {
                                if(this.participant.includes("@")) {
                                    let participantCmd = {
                                        fullName: '',
                                        email: this.participant,
                                    };
                                    this.addParticipant(participantCmd);
                                } else {
                                    this.makeToast("Input not valid. ")
                                }


                            }


                        }


                    }, 2000);

                }
            }
        }
    }
</script>

<style scoped>

</style>
