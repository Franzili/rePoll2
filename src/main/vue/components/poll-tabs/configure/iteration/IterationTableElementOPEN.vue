<template>
    <b-card
        border-variant="primary"
        :title="iteration.id"
        align="left"
        bg-variant="light"
    >
        <b-row v-if="iteration.status === ">
            <b-col cols="8">
                <p class="status">{{poll.status}}</p>
            </b-col>
            <b-col cols="4" style="text-align: center">
                <router-link class="configLink"
                             :to="{ name: 'answer', params: {id: poll.id}}"
                >Fill Out
                </router-link>
            </b-col>
        </b-row>
    </b-card>
</template>

<script>
    export default {
        name: "IterationTableElement"
    }
</script>

<style scoped>

</style>
