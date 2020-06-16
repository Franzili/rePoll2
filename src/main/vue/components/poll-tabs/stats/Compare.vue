<template>
    <b-container>
        <p>
            <ToolBar v-bind:actives="[true,true,true,true,true,true,true]"></ToolBar>
        </p>

        <p>
            {{cardList}}
        </p>

        <div>
            <b-container v-bind:key="card.id" v-for="card in cardList">
                <b-card>
                    <b-container v-bind:key="statistic.question.id" v-for="statistic in card.compSet">
                        <ChartCards v-bind:statistic="statistic"></ChartCards>
                    </b-container>
                </b-card>
            </b-container>
        </div>

        <b-button @click="showModal" > lalaa</b-button>
        <!-- TODO v-on with funtion-->
        <myModal

            v-on:getSelected="fillCompares($event)"
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
    import ChartCards from "./ChartCards";
    import ToolBar from "./ToolBar";

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
            fillCompares(compSet) {
                if (compSet.length > 0) {
                    let cardObj = {id: Date.now(), compSet}
                    this.cardList.push(cardObj)
                }
            }
        },
        components: {
            myModal,
            ChartCards,
            ToolBar
        }
    }
</script>

<style scoped>

</style>
