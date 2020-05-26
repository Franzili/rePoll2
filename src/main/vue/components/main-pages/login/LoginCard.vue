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
            this.requestPolls()
        },
        computed: {
            ...mapState({
                authenticated: 'authenticated'
            })
        },
        methods: {
            ...mapActions(['requestToken', 'requestPolls']),
            login() {
                this.requestToken(this.auth)
                    .then(() => {
                        this.$router.push("/polls");
                    })
                    .catch(() => {

                    })
            }
        },
        components: {
            ...mapState({
                polls: state => state.polls
            })
        }
    }
</script>

<style scoped>

</style>
