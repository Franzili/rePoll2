<template>
    <div :style="{ backgroundColor:'#76d4b0'}">
        <nav-bar></nav-bar>
        <HelloWorld align="center" class="ml-auto" msg="Welcome to the best Survey App!"/>

        <!-- this is better for desktop version -->

        <!--<b-container>
            <b-row align-h="center" class="login">
                <b-col></b-col>
                <b-col cols="10">
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
        </b-container> -->

        <!-- this is better for mobile version -->
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
                    required
                    placeholder="Enter password"
                ></b-form-input>
            </b-form-group>

            <div align="center">
                <b-button class="my-button" variant="success">Login</b-button>
                <b-button class="my-button" variant="secondary">Sign up</b-button>
            </div>

        </b-form>


        <!-- to fill the whole page for background -->
        <HelloWorld align="center" class="ml-auto" msg=""/>
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

    .my-button {
        margin: 10px;
    }

    .my-form {
        padding: 30px;
    }
</style>
