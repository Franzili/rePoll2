<template>
    <b-container>
        <b-row style="margin-top: 1rem">
            <b-col>
                <b-button variant="primary"
                          class="addButton"
                          @click="showModal([])" >+</b-button>
                <CheckModal
                    v-on:getSelected="fillCompares($event)"
                    ref="mymodal"
                ></CheckModal>

                <b-form-select style="max-width: 15rem; min-width: 15rem; margin-left: 3vw"
                    v-model="consistSelected"
                    :options="consistSelectOpt">
                    <template v-slot:first>
                        <b-form-select-option :value="null" disabled>consistency groups
                        </b-form-select-option>
                    </template>
                </b-form-select>

                <b-button v-if="cardList.length > 0"
                    class="float-right"
                    style="width: 50px;height: 50px;"
                    @click="deleteItemOrList(-1)">
                    <b-icon icon="trash"></b-icon>
                </b-button>
            </b-col>

        </b-row>

        <b-row style="margin-top: 25vh; color: lightgrey"
            align-h="center"
            v-show="cardList.length < 1 ">
               <b-card>
                   <h5> Please add a new comparision </h5>
               </b-card>
        </b-row>

        <b-row>
            <transition-group name="list">
                <b-container v-bind:key="card.id" v-for="card in cardList">
                    <b-card
                        class="list-item"
                        style="margin-top: 1rem; margin-bottom: 1rem"
                        border-variant="dark">
                        <CompareCards
                            v-on:close="deleteItemOrList($event)"
                            v-bind:compareData="card"></CompareCards>
                    </b-card>
                </b-container>
            </transition-group>
        </b-row>

    </b-container>
</template>

<script>
    import {mapGetters, mapState} from "vuex";
    import CheckModal from "./utils/CheckModal";
    import CompareCards from "./utils/CompareCards";

    // TODO persistence functionality
    export default {
        name: "Compare",
        data() {
            return {
                cardList: [],
                consistCards: [],
                consistSelectOpt: [],
                consistSelected: null,
                isItem: false,
                LIST_DELETE: 'Are you sure you want to delete every comparision?',
                ITEM_DELETE: 'Are you sure you want to delete this comparision?'
            }
        },
        computed: {
            ...mapState('currentPoll', {
                statistics: 'statistics',
                poll: 'poll'
            }),
            //TODO only for show
            ...mapGetters('currentPoll', {
                getPollStructure: 'statStructureObj'
            })
        },
        created() {
            this.getConsistencies()
            this.cardList = this.consistCards
        },
        methods: {
            getConsistencies() {
                for (let i = 0; i < this.poll.pollConsistencyGroups.length; i++) {
                    let group = this.poll.pollConsistencyGroups[i]
                    let statSet = []
                    group.questions.forEach(quest => {
                        statSet.push(this.statistics.find(stat => stat.question.id === quest.id))
                    })
                    let cardObj = {id: Date.now() + Math.random(),compSet: statSet, showTitle: 'Consistency ' + (i+1)}
                    this.consistSelectOpt.push({value: i, text: 'Consistency ' + (i+1)})
                    this.consistCards.push(cardObj)
                }
            },
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
            deleteItemOrList(id) {
                if (id !== -1) {
                    this.showConfirm(this.ITEM_DELETE, id)
                } else {
                    this.showConfirm(this.LIST_DELETE, id)
                }

            },
            showConfirm(warnTxt, id) {
                this.isItem = false
                this.$bvModal.msgBoxConfirm(warnTxt, {
                    title: ' ',
                    headerBgVariant: 'danger',
                    okVariant: 'secondary',
                    cancelVariant: 'primary',
                    centered: true,
                    noCloseOnBackdrop: true,
                    headerBorderVariant: 'dark',
                    footerBorderVariant: 'dark',
                    hideHeaderClose: true,
                    size: 'sm'
                }).then(value => {
                    if (value) {
                        if (id !== -1) {
                            this.cardList = this.cardList.filter(set => set.id !== id)
                        } else {
                            this.cardList = []
                        }
                    }

                }).catch(err => {
                    console.log(err)
                })
            }
        },
        watch: {
            consistSelected: function(val) {
                if (!this.cardList.includes(this.consistCards[val])) {
                    this.cardList.push(this.consistCards[val])
                }
            }
        },
        components: {
            CheckModal,
            CompareCards
        }
    }
</script>

<style scoped>

    .list-enter-active {
        transition: all 1s;
    }
    .list-enter {
        opacity: 0;
    }
    .addButton {
        font-size: 140%;
        width: 50px;
        height: 50px;
    }
</style>
