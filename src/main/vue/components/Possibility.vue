<template>
    <div>
        - {{possibility.text}}
        <button @click="$emit('del-pos', possibility.id)">x</button>
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
