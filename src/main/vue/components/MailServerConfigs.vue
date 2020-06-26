<template>
    <b-card>
        <b-container>
            <b-row class="justify-content-md-center" style="margin-bottom: 5vh; text-align: center">
                <h1>Mail Preferences</h1>
            </b-row>
            <b-row class="justify-content-md-center">
                <b-col col lg="2">
                    <div>
                        SMTP Server:
                    </div>
                </b-col>
                <b-col col lg="6">
                    <div>
                        <b-form-input placeholder="Server address" v-model="smtpAddress">
                        </b-form-input>
                    </div>
                </b-col>
                <b-col col lg="2">
                    <div>
                        <b-form-input placeholder="Port" v-model="smtpPort">
                        </b-form-input>
                    </div>
                </b-col>
            </b-row>
            <b-row class="justify-content-md-center">
                <b-col col lg="2">
                    <div>
                    </div>
                </b-col>
                <b-col col lg="6">
                    <b-button
                    @click="setServerAddress">
                        Save Changes
                    </b-button>
                </b-col>
            </b-row>
        </b-container>
    </b-card>
</template>

<script>
    import {mapActions, mapState} from "vuex";

    export default {
        name: "MailServerConfigs",
        data() {
            return {
                smtpAddress: '',
                smtpPort: ''
            }
        },
        computed: {
            ...mapState('adminConfigs', {
                configs: 'configs'
            })
        },
        methods: {
            ...mapActions('adminConfigs', {
                updateConfigs: 'updateConfigs'
                }
            ),
            setServerAddress() {
                let mailCmd = {
                    smtpAddress: this.smtpAddress,
                    smtpPort: this.smtpPort
                }
                this.updateConfigs(mailCmd)
                console.log(this.smtpAddress)
            }
        }
    }
</script>

<style scoped>

</style>
