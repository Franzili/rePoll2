<template>
    <b-card
        border-variant="primary"
        :title="poll.title"
        align="left"
        bg-variant="light"
    >
        <b-row>

            <b-col cols="8">
                <p class="status">{{poll.status}}</p>
            </b-col>

            <b-col cols="4" style="text-align: center">

                <router-link class="configLink"
                             :to="'/poll/' + poll.id + '/answer/'"
                >Fill Out
                </router-link>

            </b-col>
        </b-row>

    </b-card>
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
