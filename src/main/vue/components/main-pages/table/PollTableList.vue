<template>

    <b-container>
        <b-row>
            <b-col>
                <p>
                    <b-button  variant="primary" class="addButton" v-on:click="addNewPoll">+</b-button>
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
    import axios from "axios";

    export default {
        name: "PollTableList",
        props: ["polls"],
        methods: {
            addNewPoll() {
                let pollCmd = {title: "new Poll"};
                axios.post('/api/v1/polls/', pollCmd)
                    .then((response) => {
                        console.log(response.data);
                        return this.$router.push({name: 'create', params: {tmpPollID: response.data.id}})
                    }).catch((err) => {
                    console.log(err.message)
                });
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
