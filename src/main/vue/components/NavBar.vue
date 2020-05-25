<template>
        <b-navbar style="text-align:center;" toggleable="" type="dark" class="nav-background">
            <b-navbar-brand href="#">
                <img @click="toStart" src="../assets/logo.png" width="123" height="27">
            </b-navbar-brand>

            <b-navbar-toggle target="navbar-toggle-collapse">
                <template v-slot:default="{ expanded }">
                    <b-icon class='my-icon' v-if="expanded" icon="caret-up-fill"></b-icon>
                    <b-icon class="my-icon" v-else icon="caret-down-fill"></b-icon>
                </template>
            </b-navbar-toggle>

            <b-collapse id="navbar-toggle-collapse" is-nav>
                <b-navbar-nav class="ml-auto">
                    <b-nav-item>
                        <router-link class="link" :to="'/account/'">Account</router-link>
                    </b-nav-item>
                    <b-nav-item>
                        <router-link class="link" :to="'/polls/'">Polls</router-link>
                    </b-nav-item>
                    <b-nav-item>
                        <router-link class="link" :to="'/create/'">Create Poll</router-link>
                    </b-nav-item>
                    <b-nav-item>
                        <router-link class="link" :to="'/answer/'">Answer Poll</router-link>
                    </b-nav-item>
                    <b-nav-item>
                        <router-link class="link" :to="'/myPolls/'">My Polls</router-link>
                    </b-nav-item>
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>
</template>

<script>
    export default {
        name: "NavBar",
        methods: {
            toStart() {
                return this.$router.push('/');
            }
        }
    }
</script>

<style scoped>
    .nav-background {
        background-color: #ffffff;
    }

    .my-icon {
        color: #000000;
    }

    .link {
        color: #3eab37;
        font-size: 20px;
    }
</style>
