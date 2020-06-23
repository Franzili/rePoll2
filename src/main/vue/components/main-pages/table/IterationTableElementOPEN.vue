<template>
    <b-card
        border-variant="primary"
        :title="iteration.id"
        align="left"
        bg-variant="light"
    >
        <b-row>
            <b-col cols="8">
                <p class="status">{{iteration.status}}</p>
            </b-col>

            <b-col
                align-self="center">
                <p v-show="iteration.status === 'OPEN'"
                ><span class="start">Opened: </span>{{iteration.start}}</p>
                <p v-show="iteration.status === 'OPEN'"
                ><span class="start">Close: </span>{{iteration.end}}</p>
            </b-col>

            <b-col cols="4" style="text-align: center">
                <p>
                    <b-button class="float-right" variant="secondary" v-b-modal.closeModal>Close now</b-button>
                </p>

                <b-modal
                    id="scheduleModal"
                    ref="scheduleIterModal"
                    centered
                    title="Do you want to end this iteration ?"
                    header-bg-variant="info"
                    @ok="handleOkCloseNow">
                    <form ref="closeNowForm" @submit.stop.prevent="handleOkCloseNow">
                    </form>
                </b-modal>
            </b-col>
        </b-row>
    </b-card>
</template>

<script>

    import {mapActions} from "vuex";

    export default {
        name: "IterationTableElementOPEN",
        props: ["iteration"],
        methods: {
            isMobile() {
                return (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent));
            },
            ...mapActions('currentPoll', {
                updatePoll: 'update'
            }),
            ...mapActions('myIterations', {
                updateIteration: 'update'
            }),
            getTimeNow: function() {
                this.timestamp = new Date();
            },
            handleOkCloseNow(bvModalEvt) {
                bvModalEvt.preventDefault()
                this.getTimeNow()
                let pollIterationCmd = {
                    //start: this.timestamp,
                    //end: new Date(this.ende),
                    end: this.timestamp,
                    status: 'CLOSED'
                }
                this.createIteration(pollIterationCmd);
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
