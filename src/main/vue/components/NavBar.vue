<template>
    <b-container class="sticky nav-container" fluid="lg">
        <b-navbar toggleable="" type="dark" class="nav">
            <b-navbar-brand href="#">
                <img v-on:click="toStart" src="../assets/logo.png" width="123" height="27" alt>
            </b-navbar-brand>

            <b-navbar-toggle @click="doFunny"
                target="navbar-toggle-collapse">
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
                    <b-nav-item v-if="hasAdminPrivileges">
                        <router-link class="link" :to="'/admin/'">Admin</router-link>
                    </b-nav-item>
                    <b-nav-item>
                        <router-link class="link" :to="'/polls/'">Polls</router-link>
                    </b-nav-item>
                    <b-nav-item>
                        <b-button variant="danger" @click="logOutUser">Logout</b-button>
                    </b-nav-item>
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>
    </b-container>
</template>

<script>
    import {mapActions, mapGetters} from "vuex";
    export default {
        name: "NavBar",
        data() {
            return {
                count: 0
            }
        },
        computed: {
            ...mapGetters('auth', {
                hasAdminPrivileges: 'hasAdminPrivileges'
            })
        },
        methods: {
            ...mapActions('auth', ['logout']),
            toStart() {
                return this.$router.push('/')
            },
            logOutUser: function () {
                this.logout();
                location.reload();
                this.toStart();
            },
            doFunny() {
                if (this.count === 10) {
                    doABarrelRoll()
                    this.count = 0
                } else {
                    this.count++
                }
            }
        }
    }
    let doABarrelRoll = function(){var a="-webkit-",b='transform:rotate(1turn);',c='transition:4s;';document.head.innerHTML+='<style>body{'+a+b+a+c+b+c}
</script>

<style lang="scss" scoped>
    @import "../assets/stylesheet.scss";

    .nav {
        text-align:center;
        border-bottom: 3px solid $border-color;
        padding-left: 0;
        padding-right: 0;
    }

    .nav-container {
        z-index: $zindex-sticky;
        top: 0;
        padding-bottom: 21px;
        background-color: $floating-background-color;
    }

    .my-icon {
        color: #000000;
    }

    .link {
        color: #3eab37;
        font-size: 20px;
    }
</style>
