<template>
    <b-container>

        <p>
            {{cardList}}
        </p>

        <b-button @click="showModal" > lalaa</b-button>
        <!-- TODO v-on with funtion-->
        <myModal

            v-on:getSelected="$event !== [] && cardList.push($event)"
            ref="mymodal"
            v-bind:modalObj="getPollStructure"
        ></myModal>
        <b-button @click="cardList = []">
            kill
        </b-button>
        <p>
            {{getPollStructure}}
        </p>
    </b-container>
</template>

<script>
    import {mapGetters, mapState} from "vuex";
    import myModal from "./myModal";

    export default {
        name: "Compare",
        data() {
            return {
                cardList: []
            }
        },
        computed: {
            ...mapState('currentPoll', {
                statistics: 'statistics'
            }),
            ...mapGetters('currentPoll', {
                getPollStructure: 'statStructureObj'
            })
        },
        created() {
            //console.log('compre tab: ')
            //console.log(this.getPollStructure)
        },
        methods: {
            showModal() {
                this.$refs.mymodal.show()
            },
        },
        components: {
            myModal
        }
    }
</script>

<style scoped>

</style>
