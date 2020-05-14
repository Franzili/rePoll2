<template>
    <div class="my-back">
        <nav-bar></nav-bar>
        <div>
            <b-container class="my-container">
                <b-row style="text-align: center" class="my-row">
                    <b-col>
                        <HelloWorld style="text-align:center;" class="ml-auto" msg="Konfigurationsseite"/>
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
        alignment: center;
        background-color: white;
        margin-top: 30px;
        height: 70vh;
    }
</style>
