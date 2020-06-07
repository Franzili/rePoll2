<template>
    <b-container>
        <b-row>
            <p style="margin-left: 20vw; margin-top: 2vh"></p>
        </b-row>
        <b-row align-h="center" style="margin-top: 5vh; margin-bottom: 5vh">
            <h1>User-Configuration</h1>
        </b-row>
        <!---b-modal for Adding a new User and Editing--->
        <b-modal @ok="add_UpdateUser"
                 centered
                 id="modal-1"
                 role="dialog"
                 title="User-Configuration">
            <div class="modal-content">
                <div class="modal-body" style="padding:40px 50px;">
                    <form role="form">
                        <div class="form-group">
                            <label for="username"><span class="glyphicon glyphicon-user"></span> Username</label>
                            <input v-model="username" class="form-control" id="username" placeholder="Enter Username" type="text">
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
        <b-col>
            <b-card>
                <b-row>
                    <!----Filter---->
                    <b-col lg="6" class="my-1">
                        <b-form-group
                            label="Filter"
                            label-cols-sm="3"
                            label-align-sm="right"
                            label-size="sm"
                            label-for="filterInput"
                            class="mb-0"
                        >
                            <b-input-group size="sm">
                                <b-form-input
                                    v-model="filter"
                                    type="search"
                                    id="filterInput"
                                    placeholder="Type to Search"
                                ></b-form-input>
                                <b-input-group-append>
                                    <b-button :disabled="!filter" @click="filter = ''">Clear</b-button>
                                </b-input-group-append>
                            </b-input-group>
                        </b-form-group>
                    </b-col>

                    <b-col lg="6" class="my-1">
                        <b-form-group
                            label="Filter On"
                            label-cols-sm="3"
                            label-align-sm="right"
                            label-size="sm"
                            class="mb-0">
                            <b-form-checkbox-group v-model="filterOn" class="mt-1">
                                <b-form-checkbox value="username">Name</b-form-checkbox>
                                <b-form-checkbox value="mail">Age</b-form-checkbox>
                                <b-form-checkbox value="role">Role</b-form-checkbox>
                            </b-form-checkbox-group>
                        </b-form-group>
                    </b-col>
                </b-row>

                <!-- Main table element -->
                <b-table
                    show-empty
                    small
                    :items="items"
                    :fields="fields"
                    :filter="filter"
                    :filterIncludedFields="filterOn"
                    :sort-desc.sync="sortDesc"
                    :sort-direction="sortDirection"
                    @filtered="onFiltered"
                >
                    <template v-slot:cell(name)="row">
                        {{ row.value.username }}
                    </template>

                    <!-----Delete and Edit Button--->
                    <template v-slot:cell(actions)="row">
                        <b-button size="sm" variant="primary" v-b-modal.modal-1 v-on:isUpdate="true">
                            Edit
                        </b-button>
                        <b-button size="sm" variant="danger" @click="deleteUser(row.item)" class="mr-1">
                            Delete
                        </b-button>
                    </template>
                </b-table>
                <b-col >
                    <b-button class="addButton" v-b-modal.modal-1 v-on:isUpdate="false">+</b-button>
                </b-col>
            </b-card>
        </b-col>
    </b-container>

</template>

<script>
    import {mapActions, mapState} from "vuex";
    export default {
        name: "Admin",
        data() {
            return {
                //variable to change between add a new user or edit a user, when false: add a new user, when true: edit
                isUpdate: false,
                items: [

                    {username: 'JamesBond', mail: 'jbond@mi6.com', role: 'Admin'},

                    {username: 'JamesMartin', mail: 'jmartin@web.com', role: 'Creator'}

                ],
                fields: [
                    { key: 'username', label: 'Username', sortable: true, sortDirection: 'desc' },
                    { key: 'mail', label: 'E-Mail', sortable: true, class: 'text-center' },
                    { key: 'role', label: 'Role', sortable: true, class: 'text-center' },
                    { key: 'actions', label: 'Actions' }
                ],
                totalRows: 1,
                sortDesc: false,
                sortDirection: 'asc',
                filter: '',
                filterOn: [],
            }
        },
        computed: {
            ...mapState('myUsers', {
                users: 'users'
            })
        },
        mounted() {
            // Set the initial number of items
            this.totalRows = this.items.length
        },
        methods: {
            ...mapActions('myUsers', {
                createUser: "create",
                updateUser: 'update',
                deleteUser: 'delete',
                loadUser: 'load'
            }),
            async add_UpdateUser() {
                // Update user
                    if(this.isUpdate) {
                        this.isUpdate = false;
                        let userCmd = {
                            username: this.username,
                            password: this.password,
                            fullName: this.fullName,
                            email: this.email,
                            role: this.role
                        };
                        let user = await this.updateUser(userCmd);
                        return this.$router.put({name: 'update', params: {userId: user.id}})

                    }
                    // Add new user
                    else {
                        let userCmd = {
                            username: this.username,
                            password: this.username,
                            fullName: this.fullName,
                            email: this.email,
                            role: this.role
                        };
                        let user = await this.createUser(userCmd);
                        return this.$router.push({name: 'create', params: {userId: user.id}})
                    }

            },
            deleteUser() {
                this.deleteUser(this.user.id);
                return this.$router.push('/')
            },
            onFiltered(filteredItems) {
                this.totalRows = filteredItems.length
            }
        },
        components:{

        }
    }
</script>

<style scoped>

    .addButton {
        font-size: 140%;
        width: 50px;
        height: 50px;
    }
</style>
