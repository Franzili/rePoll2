<template>
    <div>
        <p>
            {{this.consistencies}}
        </p>
        <b-button @click="showModal([], -1)"
                  style="margin-top: 1rem" variant="primary" class="addButton">+
        </b-button>
        <CheckModal v-on:getSelected="setSelected($event)"
            ref="mymodal"></CheckModal>
        <b-container v-bind:key="item.id" v-for="(item, index) in constiTest">
            <p>
                {{item.questions}}
                {{index}}
            </p>
            <ToolBar v-on:edit="showModal(item.questions, index)"
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
                consistencies: [],
                constiTest: [
                    {
                        id: '1',
                        questions: [8,9]
                    },
                    {
                        id: '2',
                        questions: [9,8]
                    }
                ]
            }
        },
        computed: {
            ...mapState('currentPoll', {
                poll: 'poll'
            })
        },
        created() {
            this.loadPoll(this.$route.params.pollId)
            this.consistencies = this.poll.pollConsistencyGroups
        },
        methods: {
            ...mapActions('currentPoll', {
                loadPoll: 'load'
            }),
            showModal(list, index) {
                this.activeIndex = index
                this.$refs.mymodal.show(list)
            },
            setSelected(selected) {
                if (this.activeIndex === -1) {
                    this.consistencies.push(selected)
                } else {
                    this.consistencies[this.activeIndex] = selected
                    this.activeIndex = -1
                }
            }
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
