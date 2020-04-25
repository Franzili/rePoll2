<template>
        <b-navbar toggleable type="dark" class="nav-background">
            <b-navbar-brand href="#">
                <router-link :to="'/'">rePOLL</router-link>
            </b-navbar-brand>

            <b-navbar-toggle target="navbar-toggle-collapse">
                <template v-slot:default="{ expanded }">
                    <b-icon class='my-icon' v-if="expanded" icon="caret-up-fill"></b-icon>
                    <b-icon class="my-icon" v-else icon="caret-down-fill"></b-icon>
                </template>
            </b-navbar-toggle>

            <b-collapse id="navbar-toggle-collapse" is-nav>
                <b-navbar-nav class="ml-auto">
                    <b-nav-item href="#">
                        <router-link :to="'/account/'">Account</router-link>
                    </b-nav-item>
                    <b-nav-item href="#">
                        <router-link :to="'/surveys'">Surveys</router-link>
                    </b-nav-item>
                    <b-nav-item href="#">
                        <router-link :to="'/create/'">Create Survey</router-link>
                    </b-nav-item>
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>
</template>

<script>
    export default {
        name: "NavBar"
    }
</script>

<style scoped>
    .nav-background {
        background-color: #000000;
    }

    .my-icon {
        color: white;
    }
</style>
