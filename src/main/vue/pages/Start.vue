<template>
    <div :style="{ backgroundColor:'#76d4b0'}">
        <nav-bar></nav-bar>
        <HelloWorld align="center" class="ml-auto" msg="Welcome to the best Survey App!"/>
        <b-container>
            <b-row align-v="center" class="login">
                <b-col></b-col>
                <b-col>
                    <b-form>
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
                                required
                                placeholder="Enter password"
                            ></b-form-input>
                        </b-form-group>

                        <b-button variant="success">Login</b-button>
                        <b-button variant="secondary">Sign up</b-button>
                    </b-form>
                </b-col>
                <b-col></b-col>

            </b-row>
        </b-container>
        <!-- to fill the whole page for background -->
        <HelloWorld align="center" class="ml-auto" msg=""/>
        <HelloWorld align="center" class="ml-auto" msg=""/>
        <HelloWorld align="center" class="ml-auto" msg=""/>
    </div>
</template>

<script>

    import HelloWorld from '../components/HelloWorld.vue'
    import NavBar from "../components/NavBar";

    export default {
        name: "Start",
        components: {
            NavBar,
            HelloWorld
        },
    }
</script>

<style scoped>
    .login {
        padding: 130px;
    }
</style>
