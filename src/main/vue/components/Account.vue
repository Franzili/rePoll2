<template>
    <div :style="{ backgroundColor:'lightblue'}">
        <nav-bar></nav-bar>
        <HelloWorld align="center" class="ml-auto" msg="Configure your Account!"/>
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
        name: "Account",
        data() {
            return {
                backendData: "",
                form: {
                    email: '',
                    name: '',
                },
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
</style>
