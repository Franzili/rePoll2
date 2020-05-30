<template>
    <b-container>

        <b-row align-h="center" style="margin-top: 5vh; margin-bottom: 5vh">
            <b-col sm="10" md="8" lg="6" xl="5" >
                <b-img v-on:click="toTable" src="../assets/logo.png" fluid fluid-grow alr="Corporate Logo"></b-img>
            </b-col>
        </b-row>

        <b-row align-h="center" class="login">
            <LoginCard></LoginCard>
        </b-row>

    </b-container>
</template>

<script>
    import LoginCard from "../components/main-pages/login/LoginCard";

    export default {
        name: "Start",
        methods: {
            toTable() {
                return this.$router.push('/polls')
            }
        },
        components: {
            LoginCard,
        }
    }
</script>
<style scoped>
</style>
