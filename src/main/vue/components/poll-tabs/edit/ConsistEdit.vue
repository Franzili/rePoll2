<template>
    <div>
        <!--
        <p>
            {{this.consistencies}}
        </p>
        -->
        <b-row class="border-top border-primary align-items-center"
               style="margin-top: 1vh">
            <b-col cols="10">
                <span>
                    <strong>Consistency Groups</strong>
                </span>
            </b-col>
            <b-col cols="1">
                <b-button @click="showModal([], -1)"
                          style="margin-top: 1rem" variant="primary"
                          class="addButton float-right">+
                </b-button>
            </b-col>


            <CheckModal v-on:getSelected="setSelected($event)"
                        ref="mymodal"></CheckModal>
        </b-row>

        <b-row style="max-height: 35vh; overflow-y: auto; margin-top: 1vh">
            <b-container v-bind:key="group.id" v-for="(group, index) in consistencies">
                <b-col cols="10">
                    <b-row v-for="quest in group.questions"
                        v-bind:key="quest.id">
                    <span
                          class="text-truncate">{{quest.title}}</span>
                    </b-row>
                </b-col>
                <b-col>
                    <ToolBar
                        v-on:close="deleteConst(index)"
                        v-on:edit="showModal(group.questions, index)"
                        v-bind:e-size="'sm'"
                        v-bind:actives="actives">
                    </ToolBar>
                </b-col>

            </b-container>
        </b-row>

    </div>
</template>

<script>
    import CheckModal from "../stats/utils/CheckModal";
    import ToolBar from "../stats/utils/ToolBar";
    import {mapActions, mapState} from "vuex";
    export default {
        name: "ConsistEdit",
        data() {
            return {
                activeIndex: -1,
                actives: [null,null,null,null,null,null,true,true],
            }
        },
        computed: {
            ...mapState('consistencies', {
                consistencies: 'currentConsist'
            }),
            ...mapState('currentPoll', {
                poll: 'poll'
            })
        },
        created() {
            this.loadPoll(this.$route.params.pollId) // brauchte ich dich? idk
            this.loadConst(this.$route.params.pollId)
        },
        methods: {
            ...mapActions('consistencies', {
                loadConst: 'load',
                addConst: 'create',
                updConst: 'update',
                dltConst: 'delete'
            }),
            ...mapActions('currentPoll', {
                loadPoll: 'load'
            }),
            showModal(list, index) {
                this.activeIndex = index
                let ids = []
                if (list !== []) {
                    list.forEach(quest => {
                        ids.push(quest.id)
                    })
                }
                this.$refs.mymodal.show(ids)
            },
            async setSelected(selected) {
                if (this.activeIndex === -1) { //add
                    let addCmd = {
                        pollId: this.$route.params.pollId,
                        consistencyCmd: {
                            title: ' ',
                            questionIds: selected
                        }
                    }
                    await this.addConst(addCmd)
                } else { //update
                    console.log('id: ', this.consistencies[this.activeIndex].id)
                    let updCmd = {
                        pollId: this.$route.params.pollId,
                        constId: this.consistencies[this.activeIndex].id,
                        consistencyCmd: {
                            title: ' ',
                            questionIds: selected
                        }
                    }
                    await this.updConst(updCmd)

                    this.activeIndex = -1
                    this.$forceUpdate(); // not a good solution but ...
                }
            },
            deleteConst(index) {
                let dltCmd = {
                    pollId: this.$route.params.pollId,
                    constId: this.consistencies[index].id
                }
                this.dltConst(dltCmd);

            }
        },
        mounted() {
            this.$root.$on('update-questions', () => {
                this.loadConst(this.$route.params.pollId)
                this.$forceUpdate();
            })
        },
        components: {
            CheckModal,
            ToolBar
        }
    }
</script>

<style scoped>

</style>
