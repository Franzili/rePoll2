<template>
    <b-card style="margin-bottom: 5vh">
        <b-container>
            <b-modal
                centered
                title="Server Configs"
                id="setConfigs">
                <p>
                    {{ serverResponseConfigs }}
                </p>
            </b-modal>
            <b-row class="justify-content-md-center" style="margin-bottom: 5vh; text-align: center">
                <h1>Mail Preferences</h1>
            </b-row>
            <b-row class="justify-content-md-center" style="margin-bottom: 3vh">
                <b-col col lg="2">
                    <div>
                        SMTP Server:
                    </div>
                </b-col>
                <b-col col lg="4">
                    <div>
                        <b-form-input :placeholder="smtpServerAddress" v-model="smtpServerAddress">
                        </b-form-input>
                    </div>
                </b-col>
                <b-col col lg="2">
                    <div>
                        <b-form-input :placeholder="smtpPort" v-model="smtpPort">
                        </b-form-input>
                    </div>
                </b-col>
            </b-row>
            <b-row class="justify-content-md-center" style="margin-bottom: 3vh">
                <b-col col lg="2">
                    <div>
                        Mail Account:
                    </div>
                </b-col>
                <b-col col lg="6">
                    <div>
                        <b-form-input :placeholder="mailAccount" v-model="mailAccount">
                        </b-form-input>
                    </div>
                </b-col>
            </b-row>
            <b-row class="justify-content-md-center" style="margin-bottom: 3vh">
                <b-col col lg="2">
                    <div>
                        Password:
                    </div>
                </b-col>
                <b-col col lg="6">
                    <div>
                        <b-form-input
                            :placeholder="mailPassword"
                            v-model="mailPassword"
                            type="password">
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
                        v-b-modal.setConfigs
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
                mailPassword: '',
                serverResponseConfigs: ''
            }
        },
        computed: {
            ...mapState('adminConfigs', {
                configs: 'configs'
            })
        },
        methods: {
            ...mapActions('adminConfigs', {
                updateConfigs: 'updateConfigs',
                getConfigs: 'getConfigs'
                }
            ),
            async setServerAddress() {
                let mailCmd = {
                    smtpServerAddress: this.smtpServerAddress,
                    smtpPort: this.smtpPort,
                    account: this.mailAccount,
                    password: this.mailPassword
                }
                this.serverResponseConfigs = await this.updateConfigs(mailCmd)
            }
        },
        async mounted() {
            await this.getConfigs();
            this.smtpServerAddress = this.configs.hostServer.toString()
            this.smtpPort = this.configs.port.toString()
            this.mailAccount = this.configs.sendersAddress.toString()
            this.mailPassword = this.configs.senderPassword.toString()
        }
    }
</script>

<style scoped>

</style>
