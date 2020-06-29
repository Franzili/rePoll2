<template>
    <b-card
        border-variant="primary"
        align="left"
        bg-variant="light"
    >
        <b-row>
            <b-col cols="8">
                Iteration #{{iteration.id}}
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
            handleOkDelete(bvModalEvt) {
                bvModalEvt.preventDefault()

                this.deleteIteration(this.iteration.id);
                this.$nextTick(() => {
                    this.$bvModal.hide('deleteModal')
                })
            }
        }
    }
</script>

<style scoped>

</style>
