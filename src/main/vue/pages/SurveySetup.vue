<template>
    <div class="my-back">
        <nav-bar></nav-bar>
        <div class="my-sh">
            <b-container class="my-container">
                <b-row style="text-align: center" class="my-row">
                    <b-col>
                        <HelloWorld style="text-align:center;" class="ml-auto" :msg="survey.name"/>
                    </b-col>
                </b-row>

                <b-row align-h="center">
                    <b-form>
                        <b-button class="my-button">Bearbeiten</b-button>
                        <b-button class="my-button">Analyse</b-button>
                    </b-form>
                </b-row>

                <b-row>
                    <p style="margin-left: 20vw; margin-top: 2vh">Design</p>
                </b-row>

                <b-row align-h="center">
                    <b-col>
                        <p>Status</p>

                    </b-col>
                    <b-col>
                        <div>
                            <b-form-group label="Raido v3">
                                <b-form-radio-group
                                v-model="selected"
                                :options="options"
                                plain
                                name="plain-inline">
                                </b-form-radio-group>
                            </b-form-group>
                        </div>
                    </b-col>

                </b-row>
            </b-container>
        </div>
    </div>
</template>

<script>

    import NavBar from "../components/NavBar";
    import HelloWorld from "../components/HelloWorld";
    export default {
        name: "SurveySetup",
        data() {
            return {
                survey: {
                    id: 0,
                    name: '',
                    status: ''
                },
                selected: '',
                options: [
                    { text: 'In Bearbeitung', value: 'IN_PROCESSING'},
                    { text: 'Bereit', value: 'READY'},
                    { text: 'Aktiviert', value: 'ACTIVATED'},
                    { text: 'Deaktiviert', value: 'DEACTIVATED'}
                ]
            }
        },
        created() {
            this.survey = this.$route.params.thisSurvey;
        },
        components: {
            NavBar,
            HelloWorld,
        }
    }
</script>

<style scoped>
    .my-back {
        min-height: 100vh;
        background-color: lightgray
    }

    .my-container {
        text-align: center;
        box-shadow: 10px 10px 5px grey;
        alignment: center;
        background-color: #EEEDEE;
        margin-top: 30px;
        height: 70vh;
    }
    .my-button {
        background-color: black;
        font-size: 100%;
        color: #ACABAB;
        margin-left: 2vw;
        margin-right: 2vw;
    }
</style>
