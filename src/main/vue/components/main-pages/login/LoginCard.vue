<template>

    <b-col md="8" style="max-width: 360px">
        <b-card>
            <b-alert variant="danger" :show="authenticated === false">
                Incorrect username or password.
            </b-alert>

            <b-form @submit.prevent="login()">
                <b-form-group
                    label="Username">
                    <b-form-input
                        required
                        placeholder="Enter username"
                        v-model="auth.username"
                    ></b-form-input>
                </b-form-group>

                <b-form-group label="Password" label-for="input-2">
                    <b-form-input
                        type="password"
                        required
                        placeholder="Enter password"
                        v-model="auth.password"
                    ></b-form-input>
                </b-form-group>

                <b-button variant="primary" v-on:click="login()">Login</b-button>

            </b-form>
        </b-card>
    </b-col>
</template>

<script>
    import {mapActions, mapState} from "vuex";

    export default {
        name: "login-card",
        data() {
            return {
                auth: {
                    username: '',
                    password: ''
                },
            }
        },
        created() {
            this.pressKeyEnter = this.pressKeyEnter.bind(this);
            document.addEventListener('keypress', this.pressKeyEnter)
        },
        destroyed() {
            document.removeEventListener('keypress', this.pressKeyEnter)
        },
        computed: {
            ...mapState('auth', ['authenticated'])
        },
        methods: {
            ...mapActions('auth', ['requestToken']),
            login() {
                this.requestToken(this.auth)
                    .then(() => {
                        this.$router.push("/polls");
                    })
                    .catch(() => {
                    })
            },
            pressKeyEnter(e) {
                    if (e.key === 'Enter') {
                        this.requestToken(this.auth)
                            .then(() => {
                                this.$router.push("/polls");
                            })
                            .catch(() => {
                            })
                    }
            }
        }
    }
</script>

<style scoped>
</style>
