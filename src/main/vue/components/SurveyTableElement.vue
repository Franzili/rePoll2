<template>
    <div class="my-ste">
        <b-container>
            <b-row style="text-align: center" cols="5">
                <b-col>
                    <p class="my-name">{{survey.name}}</p>
                </b-col>
            </b-row>
            <b-row></b-row>
            <b-row style="text-align: center;" cols="5">
                <b-col>
                    <p class="my-status">{{survey.status}}</p>
                </b-col>
                <b-col></b-col>
                <b-col></b-col>
                <b-col></b-col>
                <b-col>
                    <p class="my-config-link">Konfigurieren</p>
                </b-col>

            </b-row>
        </b-container>
    </div>

</template>

<script>
    export default {
        name: "SurveyListElement",
        props: ["survey"]
    }
</script>

<style scoped>

    .my-config-link {
        font-size: 14px;
        color: #7F7E7F;
    }

    .my-name {
        padding-top: 5px;
        font-size: 22px;
    }

    .my-status{
        font-size: 14px;
    }
    .my-ste {
        margin: 15px;
        text-align: center;
        background-color: white;
        font-family: Arial,sans-serif;
    }

</style>
