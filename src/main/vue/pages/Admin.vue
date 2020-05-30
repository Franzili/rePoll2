<template>
    <!-- USE ONE OF THOSE TO YOU MEANS AS MOTHER -->

    <div class="my-back">
        <b-row align-h="center" style="margin-top: 5vh; margin-bottom: 5vh">
            <h1>User-Configuration</h1>
            <b-col >
                <b-button class="addButton" v-b-modal.modal-1>+</b-button>
            </b-col>
            <b-modal id="modal-1"
                     role="dialog"
                     centered
                     title="Add a new User"
                     @ok="addNewUser">
                <div class="modal-content">
                    <div class="modal-body" style="padding:40px 50px;">
                        <form role="form">
                            <div class="form-group">
                                <label for="usrname"><span class="glyphicon glyphicon-user"></span> Username</label>
                                <input type="text" class="form-control" id="usrname" placeholder="Enter Username">
                            </div>
                            <div class="form-group">
                                <label for="password"><span class="glyphicon glyphicon-eye-open"></span> Password</label>
                                <input type="text" class="form-control" id="password" placeholder="Enter Password">
                            </div>
                            <div class="form-group">
                                <label for="fullname"><span class="glyphicon glyphicon-eye-open"></span> Fullname</label>
                                <input type="text" class="form-control" id="fullname" placeholder="Enter Fullname">
                            </div>
                            <div class="form-group">
                                <label for="mail"><span class="glyphicon glyphicon-eye-open"></span> E-Mail-Adress</label>
                                <input type="text" class="form-control" id="mail" placeholder="Enter Mail">
                            </div>
                            <div class="dropdown show">
                                <div class="dropdown-menu" aria-labelledby="role">
                                   <a class="dropdown-item" href="#">Admin</a>
                                    <a class="dropdown-item" href="#">Creator</a>
                                    <a class="dropdown-item" href="#">Editor</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </b-modal>
        </b-row>

        <b-table :items="items" :fields="fields" caption-top>
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
                fields: ['Username', 'E_Mail_Adress', 'Role'],
                items: [
                    {Username: 'JamesBond', E_Mail_Adress: 'jbond@mi6.com', Role: 'Admin'}
                ]
            }
        },

        // Vue lifecycle related functions, if needed
        beforeMount() {
        },
        mounted() {
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
    .my-back {
        min-height: 100vh;
        background-color: lightgray
    }
</style>


