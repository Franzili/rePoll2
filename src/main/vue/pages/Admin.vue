<template>
    <b-container>
        <div style="margin-bottom: 5vh">
            <b-card>
                <b-row>
                </b-row>
                <b-row align-h="center" style="margin-bottom: 5vh">
                    <h1>User-Configuration</h1>
                </b-row>
                <!---b-modal for Adding a new User and Editing--->
                <b-modal @ok="add_UpdateUser"
                         centered
                         id="modal-1"
                         role="dialog"
                         title="User-Configuration">

                    <div>
                        <b-form-group
                            label="Username:"
                            label-for="username-input">
                            <b-form-input
                                id="username-input"
                                v-model="username">
                            </b-form-input>
                        </b-form-group>


                        <b-form-group
                            v-if="!isUpdate"
                            label="Password:"
                            label-for="password-input">
                            <b-form-input
                                id="password-input"
                                v-model="password">
                            </b-form-input>
                        </b-form-group>
                        <b-form-group
                            label="Fullname:"
                            label-for="fullName-input">
                            <b-form-input
                                id="fullName-input"
                                v-model="fullName">
                            </b-form-input>
                        </b-form-group>
                        <b-form-group
                            label="Email:"
                            label-for="email-input">
                            <b-form-input
                                id="email-input"
                                v-model="email">
                            </b-form-input>
                        </b-form-group>
                        <b-form-group
                            label="Role:"
                            label-for="role-input">
                            <b-form-select v-model="roles" id="role-input" :options="options">Role</b-form-select>
                        </b-form-group>
                        <b-button type="button" class="close" data-dismiss="modal-1">&times;</b-button>
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
                                    class="mb-0"
                                    data-toggle="tooltip"
                                    title="Filter by this column">
                                    <b-form-checkbox-group v-model="filterOn" class="mt-1">
                                        <b-form-checkbox value="username">Name</b-form-checkbox>
                                        <b-form-checkbox value="email">E-Mail</b-form-checkbox>
                                        <b-form-checkbox value="fullName">Fullname</b-form-checkbox>
                                    </b-form-checkbox-group>
                                </b-form-group>
                            </b-col>
                        </b-row>

                        <!-- User table -->
                        <div style="white-space: nowrap">
                        <b-table
                            show-empty
                            small
                            responsive
                            :no-border-collapse="true"
                            :sticky-header="true"
                            :items="this.users"
                            :fields="fields"
                            :filter="filter"
                            :filterIncludedFields="filterOn"
                            :sort-desc.sync="sortDesc"
                            :sort-direction="sortDirection"
                            @filtered="onFiltered"
                        >
                            <template v-slot:cell(name)="row" class="text-nowrap" style="position: sticky">
                                {{ row.value.username }}
                            </template>

                            <!-----Delete and Edit Button--->
                            <template v-slot:cell(actions)="row" class="text-nowrap">
                                <b-button size="sm"
                                          variant="primary"
                                          v-b-modal.modal-1
                                          @click="updateStart(row.item)"
                                        style="margin-right: 1vh">
                                    Edit
                                </b-button>
                                <b-button
                                    :disabled="username1===row.item.username"
                                    size="sm" variant="danger"
                                    v-b-modal.delete-modal
                                    @click="startDelete(row.item)">
                                    Delete
                                </b-button>
                            </template>
                        </b-table>
                        </div>
                        <!---- Add button to create new user--->
                        <b-col >
                            <b-button class="addButton"
                                      data-toggle="tooltip"
                                      data-placement="auto"
                                      title="Click to add a new User"
                                      v-b-modal.modal-1
                                      @click="isUpdate=false">+</b-button>
                        </b-col>
                        <b-modal id="delete-modal"
                                 centered
                                 title="Are you sure you want to delete this User?"
                                 @ok="deleteUser">
                        </b-modal>
                    </b-card>
                </b-col>
            </b-card>
        </div>
        <div>
            <MailServerConfigs></MailServerConfigs>
        </div>
    </b-container>

</template>

<script>
    import {mapActions, mapState} from "vuex";
    import MailServerConfigs from "../components/MailServerConfigs";

    export default {
        name: "Admin",
        data() {
            let role_Admin = ['ROLE_ADMIN'];
            let role_Creator = ['ROLE_POLL_CREATOR'];
            let role_Editor = ['ROLE_POLL_EDITOR'];
            return {
                isUpdate: false,
                items: [],
                //options for the drop-down
                options: [
                    { value: role_Admin, text: 'Admin'},
                    { value: role_Creator, text: 'Creator'},
                    { value: role_Editor, text: 'Editor'}
                ],
                username: '',
                password: '',
                fullName: '',
                email: '',
                roles: [],
                fields: [
                    { key: 'username', label: 'Username', sortable: true, sortDirection: 'desc' },
                    { key: 'email', label: 'E-Mail', sortable: true, class: 'text-center' },
                    { key: 'fullName', label: 'Fullname', sortable: true, class: 'text-center' },
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
            ...mapState('myUsers', {users: 'users'}),
            ...mapState('auth', { username1: 'username'})
        },
        mounted() {
            // Set the initial number of items
            this.totalRows = this.items.length
            this.loadUser()

        },
        methods: {
            ...mapActions('myUsers', {
                createUser: "create",
                updateUser: 'update',
                deleteuser: 'delete',
                loadUser: 'load'
            }),
            updateStart(row) {

                    this.isUpdate="true",
                    this.id = row.id,
                    this.username = row.username,
                    this.fullName = row.fullName,
                    this.email = row.email,
                    this.roles = row.roles,
                    this.password = row.password
            },
            add_UpdateUser() {
                // Update user
                    if(this.isUpdate) {
                        this.isUpdate = false;
                        let userCmd = {
                            id: this.id,
                            username: this.username,
                            fullName: this.fullName,
                            email: this.email,
                            password: this.password,
                            roles: this.roles,
                        };
                        this.updateUser(userCmd);

                    }
                    // Add new user
                    else {
                        let userCmd = {
                            username: this.username,
                            password: this.password,
                            fullName: this.fullName,
                            email: this.email,
                            roles: this.roles
                        };
                        this.createUser(userCmd);
                    }

            },
            startDelete(row) {
                this.id = row.id;
                this.username = row.username;
            },
            deleteUser() {
                if (this.username !== this.username1) {
                    console.debug("[Repoll] Attempting to delete user " + this.username);
                    this.deleteuser(this.id);
                    this.loadUser();
                } else {
                    console.debug("[Repoll] " + this.username + " is the current user.");
                }

            },
            onFiltered(filteredItems) {
                this.totalRows = filteredItems.length
            }
        },
        components:{
            MailServerConfigs
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
