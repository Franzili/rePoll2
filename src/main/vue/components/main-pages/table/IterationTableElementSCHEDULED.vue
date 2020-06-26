<template>
    <b-card
        border-variant="primary"
        align="left"
        bg-variant="light"
    >
        <b-row>
            <b-col cols="8">
                {{iteration.id}}
                <p class="status">{{iteration.status}}</p>
            </b-col>

            <b-col
                align-self="center">
                <p v-show="iteration.status === 'SCHEDULED'"
                ><span class="start">Open: </span>{{iteration.start}}</p>
                <p v-show="iteration.status === 'SCHEDULED'"
                ><span class="start">Open: </span>{{iteration.end}}</p>
            </b-col>

            <b-col cols="4" style="text-align: center">
                <p>
                    <b-button class="float-right" variant="secondary" v-b-modal.deleteModal>X</b-button>
                </p>

                <b-modal
                    id="deleteModal"
                    ref="deleteIterModal"
                    centered
                    title="Do you want to remove this iteration ?"
                    header-bg-variant="info"
                    @ok="handleOkDelete">
                    <form ref="closeNowForm" @submit.stop.prevent="handleOkDelete">
                    </form>
                </b-modal>
            </b-col>
        </b-row>
    </b-card>
</template>

<script>

    import {mapActions} from "vuex";

    export default {
        name: "IterationTableElementSCHEDULED",
        props: ["iteration"],
        data() {
            return {
                iterationStatus: ''
            }
        },
        methods: {
            ...mapActions('currentPoll', {
                updatePoll: 'update'
            }),
            ...mapActions('myIterations', {
                deleteIteration: 'delete'
            }),
            /*async loadTo() {
                await this.listIterations(this.iteration.id)
                return this.$router.push({
                    name: 'poll-tabbed',
                    params: {
                        iterationId: this.iteration.id
                    }
                })
            },*/
            handleOkDelete(bvModalEvt) {
                bvModalEvt.preventDefault()

                this.deleteIteration(this.iteration.id);
                /*let pollCmd = {
                    id: this.pollId,
                    status: 'READY'
                }
                this.updatePoll(pollCmd);*/
            }
        }
    }
</script>

<style scoped>

</style>
