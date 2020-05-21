<template>
    <div>
        <nav-bar></nav-bar>
        <HelloWorld style="text-align:center;" class="ml-auto" msg="Welcome to the best Survey App!"/>


        <!-- this is better for desktop version -->
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
    </div>
</template>

<script>
    import HelloWorld from '../components/HelloWorld.vue';
    import NavBar from "../components/NavBar";
    import {mapActions, mapState} from "vuex";

    export default {
        name: "Start",
        components: {
            NavBar,
            HelloWorld,
            ...mapState({
                surveys: state => state.surveys
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
                        this.$router.push("/surveys");
                    })
                    .catch(() => {

                    })
            }
        },
        ...mapActions([
            'requestSurveys'
        ]),
        created(){
        this.requestSurveys()
        },
        computed: {
            ...mapState({
                authenticated: 'authenticated'
            })
        }
    }
</script>
<style scoped>

    .my-button {
        margin: 10px;
    }

    .my-form {
        padding: 30px;
    }
</style>
