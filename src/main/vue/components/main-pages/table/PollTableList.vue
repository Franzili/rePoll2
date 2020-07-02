<template>

    <b-container>
        <b-row>
            <b-col>
                <p>
                    <b-button variant="primary"
                              class="addButton"
                              data-toggle="tooltip"
                              title="Click to create a new Poll"
                              :disabled="!hasCreatorPrivileges"
                              v-on:click="addNewPoll">+</b-button>
                </p>
            </b-col>
        </b-row>

        <b-row v-bind:key="poll.id" v-for="poll in polls">
            <b-col>
                <p>
                    <PollTableElement v-bind:poll="poll"/>
                </p>
            </b-col>
        </b-row>
    </b-container>


</template>

<script>
    import PollTableElement from "./PollTableElement";
    import {mapActions, mapGetters} from "vuex";

    export default {
        name: "PollTableList",
        props: ["polls"],
        computed: {
            ...mapGetters('auth', {
                hasCreatorPrivileges: "hasCreatorPrivileges"
            })
        },
        methods: {
            ...mapActions('myPolls', {
                createPoll: "create"
            }),
            async addNewPoll() {
                let pollCmd = {
                    title: "Unnamed Poll"
                };
                let newPoll = await this.createPoll(pollCmd);
                return this.$router.push({
                    name: 'edit-poll',
                    params: {
                        pollId: newPoll.id
                    }
                })
            }
        },
        components: {
            PollTableElement
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
