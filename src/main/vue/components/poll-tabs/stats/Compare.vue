<template>
    <b-container>

        <p>
            <ToolBar style="float: right"
                v-bind:actives="[true,true,true,true,false,false,true]"></ToolBar>
        </p>

        <p>
            {{cardList}}
        </p>

        <b-row>
            <b-container v-bind:key="card.id" v-for="card in cardList">
                <b-card border-variant="dark">
                    <CompareCards
                        v-on:close="deleteCard($event)"
                        v-bind:compareData="card"></CompareCards>
                </b-card>
            </b-container>
        </b-row>

        <b-button @click="showModal([])" > lalaa</b-button>
        <!-- TODO v-on with funtion-->
        <CheckModal
            v-on:getSelected="fillCompares($event)"
            ref="mymodal"
        ></CheckModal>
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
    import CheckModal from "./utils/CheckModal";
    import ToolBar from "./utils/ToolBar";
    import CompareCards from "./utils/CompareCards";

    // TODO persistence functionality
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
            //TODO only for show
            ...mapGetters('currentPoll', {
                getPollStructure: 'statStructureObj'
            })
        },
        created() {
            //console.log('compre tab: ')
            //console.log(this.getPollStructure)
        },
        methods: {
            showModal(list) {
                this.$refs.mymodal.show(list)
            },
            fillCompares(compSet) {
                if (compSet.length > 0) {
                    let statSet = []
                    compSet.forEach(entry => {
                        statSet.push(this.statistics.find(stat => stat.question.id === entry))
                    })
                    let cardObj = {id: Date.now() + Math.random(),compSet: statSet}
                    this.cardList.push(cardObj)
                }
            },
            deleteCard(id) {
                this.cardList = this.cardList.filter(set => set.id !== id)
            }
        },
        components: {
            CheckModal,
            ToolBar,
            CompareCards
        }
    }
</script>

<style scoped>

</style>
