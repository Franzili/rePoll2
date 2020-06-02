<template>
    <!-- USE ONE OF THOSE TO YOU MEANS AS MOTHER -->


        <div class="my-sh">
            <nav-bar></nav-bar>
            <b-row>
                <p style="margin-left: 20vw; margin-top: 2vh"></p>
            </b-row>
        <b-row align-h="center" style="margin-top: 5vh; margin-bottom: 5vh">
            <h1>User-Configuration</h1>
        </b-row>
            <b-col >
                <b-button class="addButton" v-b-modal.modal-1>+</b-button>
            </b-col>
            <b-modal @ok="addNewUser"
                     centered
                     id="modal-1"
                     role="dialog"
                     title="Add a new User">
                <div class="modal-content">
                    <div class="modal-body" style="padding:40px 50px;">
                        <form role="form">
                            <div class="form-group">
                                <label for="usrname"><span class="glyphicon glyphicon-user"></span> Username</label>
                                <input class="form-control" id="usrname" placeholder="Enter Username" type="text">
                            </div>
                            <div class="form-group">
                                <label for="password"><span class="glyphicon glyphicon-eye-open"></span> Password</label>
                                <input class="form-control" id="password" placeholder="Enter Password" type="text">
                            </div>
                            <div class="form-group">
                                <label for="fullname"><span class="glyphicon glyphicon-eye-open"></span> Fullname</label>
                                <input class="form-control" id="fullname" placeholder="Enter Fullname" type="text">
                            </div>
                            <div class="form-group">
                                <label for="mail"><span class="glyphicon glyphicon-eye-open"></span> E-Mail-Adress</label>
                                <input class="form-control" id="mail" placeholder="Enter Mail" type="text">
                            </div>
                            <div class="form-group">
                                <b-dropdown id="role"
                                            text="Select Role"
                                            class="m-2">
                                    <b-dropdown-item>Admin</b-dropdown-item>
                                    <b-dropdown-item>Creator</b-dropdown-item>
                                    <b-dropdown-item>Editor</b-dropdown-item>
                                </b-dropdown>
                            </div>
                        </form>
                    </div>
                </div>
            </b-modal>

            <b-table
                :fields="fields"
                :items="items"
                caption-top
                bordered
                outlined
            sticky-header="true">

                <template v-slot:table-caption>User</template>

            </b-table>

        </div>


</template>

<script>
    import {mapActions, mapGetters} from "vuex";
    //import axios from "axios";
    import NavBar from "../components/NavBar";

    export default {

        // Order is related to its use
        name: "Admin",
        // passed variables by e.g v-bind
        props: ["user"],
        // variables provided by this component
        data() {
            return {
                fields: [
                    {
                        key: 'username',
                        label: 'Username',
                        sortable: true
                    },
                    {
                        key: 'mail',
                        label: 'E_Mail_Adress',
                        sortable: true
                    },
                    {
                        key: 'role',
                        label: 'Role',
                        sortable: false
                    },
                ],
                filter: null,
                filterOn: [],

                items: [

                    {Username: 'JamesBond', E_Mail_Adress: 'jbond@mi6.com', Role: 'Admin'},

                    {Username: 'JamesMartin', E_Mail_Adress: 'jmartin@web.com', Role: 'Creator'}

                ]

            }
        },

        // Vue lifecycle related functions, if needed
        beforeMount() {
        },
        mounted() {
            this.$root.$on('bv::dropdown::show', bvEvent => {
                console.log('Dropdown is about to be shown', bvEvent)
            })
        },
        beforeCreate() {
        },
        created() {
        },

        // ...maps at top, functions provided by store
        computed: {
            ...mapGetters([])
        },
        methods: {
            ...mapActions([]),
            // write methods to your means
            addNewUser() {

            },
            deleteUser() {

            }

        },

        // after import, components need to be listed here as well
        comments: {
            NavBar,
        },


    }
</script>

<style scoped>

    .addButton {
        font-size: 140%;
        width: 50px;
        height: 50px;
    }
</style>


