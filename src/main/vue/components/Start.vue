<template>
    <div :style="{ backgroundColor:'#76d4b0'}">
        <nav-bar></nav-bar>
        <HelloWorld align="center" class="ml-auto" msg="Welcome to the best Survey App!"/>
        <b-container>
            <b-row align-v="center" class="login">
                <b-col></b-col>
                <b-col>
                    <b-form @submit="onSubmit" @reset="onReset" v-if="show">
                        <b-form-group
                            id="input-group-1"
                            label="Username:"
                            label-for="input-1"
                            description="We'll never share your username with anyone else."
                        >
                            <b-form-input
                                id="input-1"
                                v-model="form.email"
                                type="email"
                                required
                                placeholder="Enter username"
                            ></b-form-input>
                        </b-form-group>

                        <b-form-group id="input-group-2" label="Password:" label-for="input-2">
                            <b-form-input
                                id="input-2"
                                type="password"
                                v-model="form.name"
                                required
                                placeholder="Enter password"
                            ></b-form-input>
                        </b-form-group>

                        <b-form-group>

                        </b-form-group>

                        <b-button type="submit" variant="success">Login</b-button>
                        <b-button type="reset" variant="secondary">Sign up</b-button>
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

    import HelloWorld from './HelloWorld.vue'
    import NavBar from "./NavBar";

    export default {
        name: "Start",
        data() {
            return {
                backendData: "",
                form: {
                    email: '',
                    name: '',
                    food: null,
                    checked: []
                },
                foods: [{text: 'Select One', value: null}, 'Carrots', 'Beans', 'Tomatoes', 'Corn'],
                show: true
            }
        },
        methods: {
            onSubmit(evt) {
                evt.preventDefault()
                alert(JSON.stringify(this.form))
            },
            onReset(evt) {
                evt.preventDefault()
                // Reset our form values
                this.form.email = ''
                this.form.name = ''
                this.form.food = null
                this.form.checked = []
                // Trick to reset/clear native browser form validation state
                this.show = false
                this.$nextTick(() => {
                    this.show = true
                })
            }
        },
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
