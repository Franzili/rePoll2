<template>
    <div>
        <!-- :style="{ backgroundColor:'lightblue'}" -->
        <nav-bar></nav-bar>
        <HelloWorld style="text-align:center;" class="ml-auto" msg="Welcome to the best Survey App!"/>

        <!-- this is better for mobile version -->
        <div v-if="isMobile()">
            <b-form class="my-form">
                <b-form-group
                    label="Username:"
                    description="We'll never share your username with anyone else.">
                    <b-form-input
                        required
                        placeholder="Enter username"
                    ></b-form-input>
                </b-form-group>

                <b-form-group label="Password:" label-for="input-2">
                    <b-form-input
                        type="password"
                        required
                        placeholder="Enter password"
                    ></b-form-input>
                </b-form-group>

                <div>
                    <b-button class="my-button" variant="success">Login</b-button>
                    <b-button class="my-button" variant="secondary">Sign up</b-button>
                </div>

            </b-form>
        </div>

        <!-- this is better for desktop version -->
        <div v-else>
            <b-container>
                <b-row align-h="center" class="login">
                    <b-col></b-col>
                    <b-col>
                        <b-form>
                            <b-form-group
                                label="Username:">
                                <b-form-input
                                    required
                                    placeholder="Enter username"
                                    v-bind="auth.username"
                                ></b-form-input>
                            </b-form-group>

                            <b-form-group label="Password:" label-for="input-2">
                                <b-form-input
                                    required
                                    placeholder="Enter password"
                                    v-bind="auth.password"
                                ></b-form-input>
                            </b-form-group>

                            <b-button variant="primary" v-on:click="login()">Login</b-button>
                            <b-button variant="secondary">Sign up</b-button>
                        </b-form>
                    </b-col>
                    <b-col></b-col>

                </b-row>
            </b-container>
        </div>
    </div>
</template>

<script>
    import HelloWorld from '../components/HelloWorld.vue';
    import NavBar from "../components/NavBar";

    import {mapActions, mapGetters} from "vuex";

    export default {
        name: "Start",
        components: {
            NavBar,
            HelloWorld
        },
        data() {
            return {
                auth: {
                    username: '',
                    password: ''
                }
            }
        },
        computed: {
            ...mapGetters({
                authenticated: 'isAuthenticated'
            })
        },
        methods: {
            isMobile() {
                return (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent));
            },
            ...mapActions(['requestToken']),
            login() {
                console.log("MOIN MOIN IHR LANDRATTEN!");
                this.requestToken(this.auth).then(() => this.$router.push('/'))
                console.log("Landratten2");
            }
        },
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
