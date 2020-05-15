<template>
    <div class="my-back">
        <nav-bar></nav-bar>
        <div class="my-sh">
            <b-container class="my-container">
                <b-row style="text-align: center" class="my-row">
                    <b-col>
                        <HelloWorld style="text-align:center;" class="ml-auto" msg="Konfigurationsseite"/>
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
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="inlineRadioOptions" id="iRadio1" value="option1" checked>
                            <label class="form-check-label" for="iRadio1">In Bearbeitung</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="inlineRadioOptions" id="iRadio2" value="option2">
                            <label class="form-check-label" for="iRadio2">Bereit</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="inlineRadioOptions" id="iRadio3" value="option3">
                            <label class="form-check-label" for="iRadio3">Aktiviert</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="inlineRadioOptions" id="iRadio4" value="option4">
                            <label class="form-check-label" for="iRadio4">Deaktiviert</label>
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
