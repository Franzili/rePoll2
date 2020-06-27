<template>
    <div>
        <!--
        <p>
            {{this.consistencies}}
        </p>
        -->
        <b-button @click="showModal([], -1)"
                  style="margin-top: 1rem" variant="primary" class="addButton">+
        </b-button>
        <CheckModal v-on:getSelected="setSelected($event)"
            ref="mymodal"></CheckModal>
        <b-container v-bind:key="group.id" v-for="(group, index) in consistencies">
            <!--
            <p>
                {{group.questions}}
                {{index}}
            </p>
            -->
            <b-row v-bind:key="title.id" v-for="title in group.questions">
                {{title.title}}
            </b-row>


            <ToolBar v-on:edit="showModal(group.questions, index)"
                     v-bind:e-size="'sm'"
                     v-bind:actives="actives"></ToolBar>
            <!--<ConsistGroup v-bind:item="item"></ConsistGroup>-->
        </b-container>
    </div>
</template>

<script>
    import CheckModal from "../stats/utils/CheckModal";
    import ToolBar from "../stats/utils/ToolBar";
    //import ConsistGroup from "./ConsistGroup";
    import {mapActions, mapState} from "vuex";

    export default {
        name: "ConsistEdit",
        data() {
            return {
                activeIndex: -1,
                actives: [null,null,null,null,null,null,true,true],
                loadedConsist: [],
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
            this.loadConst(this.$route.params.pollId)
            this.loadedConsist = this.consistencies
        },
        methods: {
            ...mapActions('consistencies', {
                loadConst: 'load',
                addConst: 'create',
                updConst: 'update'
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
            },/*
            buildConst(selected) {
                let consist = []
                this.poll.questions.forEach(quest => {
                    selected.forEach(id => {
                        if (id === quest.id) {
                            consist.push(quest)
                        }
                    })
                })
                return consist
            }*/
        },
        components: {
            //ConsistGroup,
            CheckModal,
            ToolBar
        }
    }
</script>

<style scoped>

</style>
