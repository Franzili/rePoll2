<template>
    <div>
        <!-- better for mobile version -->
        <div v-if="isMobile()" class="my-ste">
            <b-container>
                <b-row style="text-align: center">
                    <b-col>
                        <p class="my-name">{{survey.title}}</p>
                    </b-col>
                </b-row>

                <b-row>
                    <b-col>
                        <p class="my-status">{{survey.status}}</p>
                    </b-col>
                    <b-col>
                        <p>
                            <b-icon icon="gear" animation="spin" font-scale="2" @click="toConfig"/>
                        </p>
                    </b-col>

                </b-row>
                <b-row></b-row>
            </b-container>
        </div>

        <!-- better for Desktop version -->
        <div v-else class="my-ste">
            <b-container>
                <b-row style="text-align: center" cols="5">
                    <b-col>
                        <p class="my-name">{{survey.title}}</p>
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
                        <router-link class="my-config-link" :to="'/survey/' + survey.id + '/answer/'">Fill Out</router-link>
                    </b-col>

                </b-row>
            </b-container>
        </div>
    </div>


</template>

<script>
    export default {
        name: "AnswerSurveyListElement",
        props: ["survey"],
        methods: {
            isMobile() {
                return (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent));
            },
            toConfig() {
               // return this.$router.push('/survey/' + survey.id + '/answer/')
            }
        }

    }
</script>

<style scoped>

    .my-config-link {
        font-size: 18px;
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
