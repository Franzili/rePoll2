<template>
    <div>
        <nav-bar></nav-bar>

        <div style="text-align:center;">
            <HelloWorld class="ml-auto" msg="Configure your Account!"/>

            <p class="greeting">Hello User!</p>
            <HelloWorld class="ml-auto" msg=""/>

            <p class="fake-link">Change Email</p>
            <p class="fake-link">Change password</p>

            <HelloWorld class="ml-auto" msg=""/>
            <p>Notifications:</p>
            <b-container>
                <b-row>
                    <b-col></b-col>
                    <b-col cols="5">
                        <p>get notification if i get a new survey</p>
                    </b-col>
                    <b-col>
                        <b-form-group id="input-group-1">
                            <b-form-checkbox-group>
                                <b-form-checkbox value="c1"></b-form-checkbox>
                            </b-form-checkbox-group>
                        </b-form-group>
                    </b-col>
                    <b-col></b-col>
                </b-row>

                <b-row>
                    <b-col></b-col>
                    <b-col cols="5">
                        <p>get notification when a survey expires</p>
                    </b-col>
                    <b-col>
                        <b-form-group id="input-group-2">
                            <b-form-checkbox-group>
                                <b-form-checkbox value="c1"></b-form-checkbox>
                            </b-form-checkbox-group>
                        </b-form-group>
                    </b-col>
                    <b-col></b-col>
                </b-row>


            </b-container>

            <HelloWorld class="ml-auto" msg=""/>
            <b-button class="my-button" variant="success">Save changes</b-button>
            <b-button class="my-button" variant="secondary">Reset to default</b-button>
        </div>
    </div>
</template>

<script>

    import HelloWorld from '../components/HelloWorld.vue'
    import NavBar from "../components/NavBar";

    export default {
        name: "Account",
        components: {
            NavBar,
            HelloWorld
        },
    }
</script>

<style scoped>
    .greeting {
        font-size: 30px;
    }

    .fake-link {
        color: #0000ff;
    }

    .my-button {
        margin: 10px;
    }
</style>
