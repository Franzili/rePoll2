<template>
    <b-card>
        <b-container>
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
                        <b-form-input :placeholder="mailPassword" v-model="mailPassword">
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
                smtpServerAddress: 'smtp.gmail.com',
                smtpPort: '578',
                mailAccount: 'repoll@gmail.com',
                mailPassword: 'kl?sfp19n3!20'
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
                await this.updateConfigs(mailCmd)
            }
        },
        async mounted() {
            console.log("hallo")
            await this.getConfigs();
        }
    }
</script>

<style scoped>

</style>
