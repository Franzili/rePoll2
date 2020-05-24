<template>
    <div>
        <!-- better for mobile version -->
        <div v-if="isMobile()" class="my-ste">
        </div>

        <!-- better for Desktop version -->
        <div v-else class="my-ste">
            <b-container>
                <b-row style="text-align: center" cols="5">
                    <b-col>
                        <p class="my-name">{{poll.title}}</p>
                    </b-col>
                </b-row>
                <b-row></b-row>
                <b-row style="text-align: center;" cols="5">
                    <b-col></b-col>
                    <b-col></b-col>
                    <b-col></b-col>
                    <b-col>
                        <router-link class="my-config-link" :to="'/poll/' + poll.id + '/answer/'">Fill Out</router-link>
                    </b-col>

                </b-row>
            </b-container>
        </div>
    </div>


</template>

<script>
    export default {
        name: "AnswerPollTableElement",
        props: ["poll"],
        methods: {
            isMobile() {
                return (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent));
            },
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
    .my-ste {
        margin: 15px;
        text-align: center;
        background-color: white;
        font-family: Arial,sans-serif;
    }



</style>
