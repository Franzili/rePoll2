<template>
    <component v-if="!editing" :is="tag">{{value}}</component>
    <input v-else v-model="currentValue"/>
</template>

<script>
    export default {
        name: "EditableLabel",
        data() {
            return {
                currentValue: this.value
            }
        },
        props: {
            tag: {
                required: false,
                type: String,
                default: "span"
            },
            value: {
                required: false,
                type: String,
                default: ""
            },
            editing: {
                required: false,
                type: Boolean,
                default: false
            }
        },
        watch: {
            currentValue: function() {
                this.$emit('valueChanged', this.currentValue)
            }
        }
    }
</script>

<style scoped>

</style>
