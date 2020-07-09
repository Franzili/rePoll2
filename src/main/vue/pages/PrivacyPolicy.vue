<template>
    <b-card style="margin-bottom: 5vh">
        <b-row class="justify-content-md-center" style="margin-bottom: 5vh; text-align: center">
            <h1>Privacy Policy</h1>
        </b-row>

        <!--             v-model="privacyPolicyText" -->
        <b-form-textarea
            rows="3"
            max-rows="8">

        </b-form-textarea>



        <template>
            <div v-html="privacyPolicyText"></div>
        </template>



    </b-card>

</template>

<script>
    import axios from 'axios'
    export default {
        name: "PrivacyPolicy",
        data() {
            return {
                privacyPolicyText: ""
            }
        },
        created: async function() {
            try {
                this.privacyPolicyText = (await axios.get('/api/v1/fineprint/')).data
                 console.log(this.privacyPolicyText)
            } catch {
                console.warn("[Repoll] error getting fine print")
            }

        }
    }
</script>

<style scoped>

</style>
