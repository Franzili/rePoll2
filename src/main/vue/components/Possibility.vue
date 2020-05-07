<template>
    <div>
        <b-container class="bv-example-row">
            <b-row>
                <b-col>
                    - {{possibility.text}}
                </b-col>
                <b-col>
                    <button @click="$emit('del-pos', possibility.id)">x</button>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>
    export default {
        name: "Possibility",
        props: ["possibility"]
    }
</script>

<style scoped>

</style>
