<template>
    <div :style="{ backgroundColor:'lightblue'}">
        <nav-bar></nav-bar>
        <HelloWorld align="center" class="ml-auto" msg="Create your own Survey!"/>

        <b-container class="my-contaier">

            <b-row align="center" class="my-row" cols="3">
                <b-col>Edit Area</b-col>
                <b-col>Beschreibung der Umfrage</b-col>
                <b-col>Edit elements:</b-col>
            </b-row>

            <b-row align="center" class="my-row">
                <b-col></b-col>
                <b-col>
                    How are you
                    <b-form-group id="input-group-1">
                        <b-form-checkbox-group v-model="form.checked" id="checkboxes-4">
                            <b-form-checkbox value="c1">very good</b-form-checkbox>
                            <b-form-checkbox value="c2">good</b-form-checkbox>
                            <b-form-checkbox value="c3">not good</b-form-checkbox>
                            <b-form-checkbox value="c4">bad</b-form-checkbox>
                        </b-form-checkbox-group>
                    </b-form-group>
                </b-col>
                <b-col>
                    <b-button type="submit" variant="secondary">edit</b-button>
                    <b-button type="submit" variant="danger">delete</b-button>
                </b-col>
            </b-row>

            <b-row align="center" class="my-row">
                <b-col></b-col>
                <b-col>Here Should be a Slider</b-col>
                <b-col>
                    <b-button type="submit" variant="secondary">edit</b-button>
                    <b-button type="submit" variant="danger">delete</b-button>
                </b-col>
            </b-row>

        </b-container>

        <!-- to fill the whole page for background -->
        <HelloWorld align="center" class="ml-auto" msg=""/>
        <HelloWorld align="center" class="ml-auto" msg=""/>
        <HelloWorld align="center" class="ml-auto" msg=""/>
        <HelloWorld align="center" class="ml-auto" msg=""/>
        <HelloWorld align="center" class="ml-auto" msg=""/>
        <HelloWorld align="center" class="ml-auto" msg=""/>
        <HelloWorld align="center" class="ml-auto" msg=""/>
        <HelloWorld align="center" class="ml-auto" msg=""/>
    </div>
</template>

<script>

    import HelloWorld from './HelloWorld.vue'
    import NavBar from "./NavBar";

    export default {
        name: "CreateSurvey",
        data() {
            return {
                backendData: "",
                form: {
                    email: '',
                    name: '',
                    checked: []
                },
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

    .my-row {
        padding: 20px;
    }
</style>
