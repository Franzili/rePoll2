<template>
    <div class="my-back">
        <nav-bar></nav-bar>
        <div class="my-sh">
            <b-container class="my-container">

                <!-- -->
                <b-row style="text-align: center" class="my-row">
                    <b-col>
                        <HelloWorld style="text-align:center;" class="ml-auto" :msg="poll.title"/>
                    </b-col>
                </b-row>

                <b-row align-h="center">
                    <b-form>
                        <b-button class="my-button">Bearbeiten</b-button>
                        <b-button class="my-button">Analyse</b-button>
                    </b-form>
                </b-row>

                <!-- anonymity -->
                <b-row align-h="center"
                       :aria-disabled="poll.status === 'READY'
                       || poll.status === 'ACTIVATED'
                       || poll.status === 'DEACTIVATED'">


                    <div align="left">
                        <div align="center">
                            <h3>Anonymity</h3>
                        </div>
                        <b-form-group >
                            <b-form-radio-group
                                v-on:change="changeAnonymityQuestion"
                                v-model="anonymityChecked"
                                stacked >
                                <b-form-radio value="ANONYMOUS">anonymous<br>
                                    <small class="text-muted">
                                        The participants are unknown and there are no data
                                        about them. You get one link for all participants.
                                    </small>
                                </b-form-radio>
                                <b-form-radio value="PARTIALLY_ANONYMOUS">partitially anonymous<br>
                                    <small class="text-muted">
                                        Es sind nur minimal Daten (i.d.R. ein technischer Schlüssel) vom
                                        Teilnehmer bekannt. Außer dem technischen Schlüssel werden keine
                                        Teilnehmerdaten persistiert.
                                    </small>
                                </b-form-radio>
                                <b-form-radio value="NON_ANONYMOUS">non-anonymous<br>
                                    <small class="text-muted">
                                        Der Teilnehmer ist bekannt. Es liegen Daten vor. Jeder Teilnehmer
                                        erhält einen personalisierten Link.
                                    </small>
                                </b-form-radio>
                            </b-form-radio-group>
                        </b-form-group>
                        <!-- Warning box for changes -->
                        <b-alert class="alert-info" role="alert" v-model="sureToChangeAnonymity">
                            <strong>Warning!</strong>
                            Are you sure that you want to change the level of anonymity to {{anonymityChecked}}?
                            <b-button @click="changeAnonymity" class="my-button3">yes</b-button>
                            <b-button @click="dontChangeAnonymity" class="my-button3">no</b-button>
                        </b-alert>
                    </div>
                </b-row>

                <b-row>
                    <p style="margin-left: 20vw; margin-top: 2vh">Design</p>
                </b-row>

                <!-- Freaking Cool Radio Buttons -->
                <b-row align-h="center">
                    <b-col>
                        <p>Status</p>
                        <p>{{ poll.status }}</p>
                    </b-col>
                    <b-col>
                        <div>
                            <b-form-group label="Raido v6.7">
                                <b-form-radio-group
                                v-model="selected"
                                plain
                                name="plain-inline">
                                    <b-form-radio value="IN_PROCESS"
                                    :disabled="poll.status === 'READY'
                                    || poll.status === 'ACTIVATED'
                                    || poll.status === 'DEACTIVATED'">In Bearbeitung</b-form-radio>
                                    <b-form-radio value="READY"
                                    :disabled="poll.status === 'ACTIVATED'
                                    || poll.status === 'DEACTIVATED'">Bereit</b-form-radio>
                                    <b-form-radio value="ACTIVATED"
                                    :disabled="poll.status === 'DEACTIVATED'">Aktiviert</b-form-radio>
                                    <b-form-radio value="DEACTIVATED">Deaktiviert</b-form-radio>
                                </b-form-radio-group>
                            </b-form-group>
                        </div>
                    </b-col>
                </b-row>

                <b-row>
                    <p>{{selected}}</p>
                    <p>{{anonymityChecked}}</p>
                    <p>{{this.poll.anonymity}}</p>
                </b-row>

                <!-- Warning Modal Component -->
                <b-row align-h="center">
                    <div>
                        <b-button class="my-button2" v-b-modal.modal-center>Anwenden</b-button>
                        <b-modal v-if="isStatusChange()"
                                 id="modal-center"
                                 centered
                                 title="Warnung: Status wurde geändert!"
                                 header-bg-variant="danger"
                                 @ok="handleOk">
                            <p class="my-4">Wenn Sie den Status ändern kann er nicht mehr in einen
                                früheren Status gewechselt werden.</p>
                        </b-modal>
                    </div>
                </b-row>

            </b-container>
        </div>
    </div>
</template>

<script>

    import NavBar from "../components/NavBar";
    import HelloWorld from "../components/HelloWorld";
    import {mapActions, mapGetters} from "vuex";
    import axios from 'axios';

    export default {
        name: "PollSetup",
        data() {
            return {
                tmpID: 0,
                selected: '',
                anonymityChecked: 'NON_ANONYMOUS',
                sureToChangeAnonymity: false
            }
        },
        created: function() {
            this.tmpID = this.$route.params.tmpPollID
            this.requestPoll(this.tmpID)
        },
        computed: {
            ...mapGetters(['getPoll']),
            poll() {
                return this.getPoll(this.tmpID)
            }
        },
        methods: {
            changeAnonymityQuestion: function () {
                console.log("Hallo!")
                this.sureToChangeAnonymity = true
            },
            changeAnonymity() {
                this.sureToChangeAnonymity = false
                this.poll.anonymity = this.anonymityChecked
                let pollCmd = this.poll
                console.log("change")
                console.log(this.poll.id)
                axios.put('/api/v1/polls/'+ this.poll.id + '/', pollCmd)
                    .then((response) => {
                        console.log(response.data)
                    }).catch((err) => {
                    console.log(err.message)
                })
            },
            dontChangeAnonymity() {
                this.sureToChangeAnonymity = false
                this.anonymityChecked = this.poll.anonymity
            },
            isStatusChange() {
                if (this.selected === '') {
                    return false
                }
                return !(this.selected === this.poll.status)
            },
            handleOk() {
                this.poll.status = this.selected
                let pollCmd = this.poll
                console.log(this.poll.id)
                axios.put('/api/v1/polls/'+ this.poll.id + '/', pollCmd)
                    .then((response) => {
                        console.log(response.data)
                    }).catch((err) => {
                    console.log(err.message)
                })
            },
            ...mapActions(['requestPoll'])
        },

        components: {
            NavBar,
            HelloWorld,
        },
    }
</script>

<style scoped>
    .my-back {
        min-height: 130vh;
        background-color: lightgray
    }

    .my-container {
        text-align: center;
        box-shadow: 10px 10px 5px grey;
        alignment: center;
        background-color: #EEEDEE;
        margin-top: 30px;
        height: 100vh;
    }
    .my-button {
        background-color: black;
        font-size: 100%;
        color: #ACABAB;
        margin-left: 2vw;
        margin-right: 2vw;
    }

    .my-button2 {
        background-color: lightgray;
        font-size: 100%;
        color: black;
        margin-left: 2vw;
        margin-right: 2vw;
    }

    .my-button3 {
        background-color: InfoBackground;
        font-size: 100%;
        color: black;
        margin-left: 4vw;
        margin-right: 0vw;
    }
</style>
