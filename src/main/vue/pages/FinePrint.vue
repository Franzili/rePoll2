<template>


        <footer>
            <div
                style="margin-top: 20px"
                class="text-muted"
                v-html="privacyPolicyText"></div>
        </footer>


</template>

<script>
    import axios from 'axios'
    export default {
        name: "FinePrint",
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
