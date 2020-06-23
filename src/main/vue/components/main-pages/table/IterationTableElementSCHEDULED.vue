<template>
    <b-card
        border-variant="primary"
        :title="iteration.id"
        align="left"
        bg-variant="light"
    >
        <b-row>
            <b-col cols="8">
                Open:
                <p class="status">{{iteration.start}}</p>
                Close
                <p class="status">{{iteration.end}}</p>
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
                    <form ref="closeNowForm" @submit.stop.prevent="handleSubmitDelete">
                    </form>
                </b-modal>
            </b-col>
        </b-row>
    </b-card>
</template>

<script>

    import {mapActions} from "vuex";

    export default {
        name: "IterationTableElementSCHEDULED"
        props: ["iteration"],
        methods: {
            isMobile() {
                return (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent));
            },
            ...mapActions('currentPoll', {
                updatePoll: 'update'
            }),
            ...mapActions('myIterations', {
                deleteIteration: 'delete'
            }),
            handleOkCloseNow(bvModalEvt) {
                bvModalEvt.preventDefault()

                this.deleteIteration(iteration.id);
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

    /*.my-config-link {
        font-size: 18px;
        color: #7F7E7F;
    }

    .my-name {
        padding-top: 5px;
        font-size: 22px;
    }
    .my-ste {
        margin: 15px;
        text-align: center;
        background-color: white;
        font-family: Arial,sans-serif;
    }*/

</style>
