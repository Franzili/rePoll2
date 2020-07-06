<template>

    <div>
        <input type="file"
               data-toggle="tooltip"
               title="Select a .csv-file to import"
               data-placement="auto"
               id="selectFile"
               value="Import"
               accept=".csv"
               style="margin-bottom: 2vh"/><br />
        <b-button data-toggle="tooltip"
                  title="Import data"
                  @click="handleFiles">Import File</b-button>
        <div class="output"></div>


    </div>



</template>

<script>


    import {mapActions} from "vuex";
    export default {
        name: "UploadParticipants",
        data() {
            return {
                newParticipant: '',
                participant: [],
            }
        },
        methods: {
            ...mapActions('participants', {create: "create"}),
               handleFiles() {
                const input = document.getElementById('selectFile').files;
                const reader = new FileReader();
                const csv = input[0];
                var newParticipant;
                reader.onload = e =>  {
                    document.querySelector('.output').innerText = e.target.result;
                    newParticipant = e.target.result;
                };
                  reader.readAsText(csv);
                  setTimeout(() => {
                      this.newParticipant = newParticipant;
                      var res = this.newParticipant.split("\n");
                      for(var i=0; i < res.length-1; i++) {
                          this.participant = res[i];
                          var tmp = this.participant.split(",");
                          let participantCmd = {
                              fullName: tmp[0],
                              email: tmp[1],
                          };
                          this.create(participantCmd);
                      }


                  }, 2000);


            }
        }
    }
</script>

<style scoped>

</style>
