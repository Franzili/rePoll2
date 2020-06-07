<template>
    <!-- USE ONE OF THOSE TO YOU MEANS AS MOTHER -->

    <b-container class="descriptiveLocalStyleClass">
        <!-- FILLED WITH COOL BOOTSTRAP VUE HTML STUFF-->
    </b-container>
    <!-- OR -->
    <div>
        <!-- FILLED WITH COOL BOOTSTRAP VUE HTML STUFF-->
    </div>
    <!-- OR -->
    <b-card>
        <!-- FILLED WITH COOL BOOTSTRAP VUE HTML STUFF-->
    </b-card>
    <!-- OR -->
    <b-row>
        <!-- FILLED WITH COOL BOOTSTRAP VUE HTML STUFF-->
    </b-row>

    <!-- try to use mainly b-[something] cause, powerful resizing possibility -->

</template>

<script>
    import {mapActions, mapGetters} from "vuex";
    import axios from "axios";

    export default {

        // Order is related to its use
        name: "VueTemplate",
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

        // ...maps at top, functions provided by index
        computed: {
            ...mapGetters([]),
            _ImSomethingElse() {
            }
        },
        methods: {
            ...mapActions([]),
            // write methods to your means
            ImAMethode() {
                // axios example
                axios.post('/api/v1/polls/', /* usw here Cmd objects, refs backend*/)
                    // every axios request results in a response for the frontend
                    .then((response) => {
                        console.log(response.data);
                        // do stuff here if needed ...
                    // catch errors for debugging
                    }).catch((err) => {
                    console.log(err.message)
                });
            }

        },

        // after import, components need to be listed here as well
        components: {
            //comp1,
            //comp2,
        }

    }
</script>

<style scoped>
    .descriptiveLocalStyleClass {

    }

</style>
