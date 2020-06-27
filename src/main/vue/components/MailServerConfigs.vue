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
                        <b-form-input placeholder="Server address" v-model="smtpServerAddress">
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
                <div>
                    <b-form-input placeholder="zizimeyer4@gmail.com" v-model="mailAccount">
                    </b-form-input>
                </div>
            </b-row>
            <b-row class="justify-content-md-center">
                <div>
                    <b-form-input placeholder="myPassword" v-model="mailPassword">
                    </b-form-input>
                </div>
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
                smtpServerAddress: '',
                smtpPort: '',
                mailAccount: '',
                mailPassword: ""
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
                    smtpServerAddress: this.smtpServerAddress,
                    smtpPort: this.smtpPort,
                    account: this.mailAccount,
                    password: this.mailPassword
                }
                this.updateConfigs(mailCmd)
                console.log(this.smtpServerAddress)
            }
        }
    }
</script>

<style scoped>

</style>
