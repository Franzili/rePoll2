<template>
    <!-- USE ONE OF THOSE TO YOU MEANS AS MOTHER -->

    <div class="my-back">
        <nav-bar></nav-bar>
        <b-row align-h="center">
            <h1>User-Configuration</h1>
        </b-row>
            <b-col >
                <b-button class="addButton">+</b-button>
            </b-col>
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
        props: [],
        // variables provided by this component
        data() {
            return {
                sampleStuff: "hi"
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


