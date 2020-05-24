<template>
    <b-container>
        <HelloWorld style="text-align:center;" class="ml-auto" msg="Welcome to the best Poll App!"/>


        <b-row align-h="center" class="login" fluid="md">
            <b-col md="4">
                <b-card>
                    <b-alert variant="danger" :show="authenticated === false">
                        Username and Password do not match :( Please try again!
                    </b-alert>
                    <b-form @submit.prevent="login()">
                        <b-form-group
                            label="Username:">
                            <b-form-input
                                required
                                placeholder="Enter username"
                                v-model="auth.username"
                            ></b-form-input>
                        </b-form-group>

                        <b-form-group label="Password:" label-for="input-2">
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
        </b-row>
    </b-container>
</template>

<script>
    import HelloWorld from '../components/HelloWorld.vue';
    import {mapActions, mapState} from "vuex";

    export default {
        name: "Start",
        components: {
            HelloWorld,
            ...mapState({
                polls: state => state.polls
            })
        },
        data() {
            return {
                auth: {
                    username: '',
                    password: ''
                },
            }
        },
        methods: {
            isMobile() {
                return (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent));
            },
            ...mapActions(['requestToken']),
            login() {
                this.requestToken(this.auth)
                    .then(() => {
                        this.$router.push("/polls");
                    })
                    .catch(() => {

                    })
            }
        },
        ...mapActions([
            'requestPolls'
        ]),
        created(){
        this.requestPolls()
        },
        computed: {
            ...mapState({
                authenticated: 'authenticated'
            })
        }
    }
</script>
<style scoped>
</style>
