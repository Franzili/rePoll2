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
                    <b-form-select v-model="role" id="role-input" :options="options">Role</b-form-select>
                </b-form-group>
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
                                <b-form-checkbox value="email">E-Mail</b-form-checkbox>
                                <b-form-checkbox value="fullName">Fullname</b-form-checkbox>
                            </b-form-checkbox-group>
                        </b-form-group>
                    </b-col>
                </b-row>

                <!-- User table -->
                <b-table
                    show-empty
                    small
                    :items="this.users"
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
                        <b-button size="sm" variant="primary" v-b-modal.modal-1 @click="isUpdate=true">
                            Edit
                        </b-button>
                        <b-button size="sm" variant="danger" @click="deleteUser(row.item)" class="mr-1">
                            Delete
                        </b-button>
                    </template>
                </b-table>
                <!---- Add button to create new user--->
                <b-col >
                    <b-button class="addButton" v-b-modal.modal-1 @click="isUpdate=false">+</b-button>
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
                isUpdate: false,
                items: [],
                options: [
                    { value: 'ROLE_ADMIN', text: 'Admin'},
                    { value: 'ROLE_POLL_CREATOR', text: 'Creator'},
                    { value: 'ROLE_POLL_EDITOR', text: 'Editor'}
                ],
                username: '',
                password: '',
                fullName: '',
                email: '',
                role: '',
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
            ...mapState('myUsers', {
                users: 'users'
            })
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
            add_UpdateUser() {
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
                        this.updateUser(userCmd);

                    }
                    // Add new user
                    else {
                        let userCmd = {
                            username: this.username,
                            password: this.password,
                            fullName: this.fullName,
                            email: this.email,
                            role: this.role
                        };
                        console.log(userCmd);
                        this.createUser(userCmd);
                    }

            },
            deleteUser(row) {
                console.log(row);
                this.deleteuser(row.id);
                this.loadUser();
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
