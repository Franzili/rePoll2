<template>
    <div>
        <EditableLabel :editing="editing"
                       tag="span"
                       :value="model.description"
                       v-on:valueChanged="model.description = $event"
                       class="text-muted"/>
    </div>
</template>

<script>
    import EditableLabel from "../../../EditableLabel";

    export default {
        name: "SectionHeader",
        props: {
            model: {
                type: Object,
                required: true
            },
            editing: {
                type: Boolean,
                required: false,
                default: false
            },
            editable: {
                type: Boolean,
                required: false,
                default: true
            }
        },
        watch: {
            model: {
                handler: function() {
                    console.log("Emitting!");
                    this.$emit('modelChanged', this.model)
                },
                deep: true
            }
        },
        components: {
            EditableLabel
        }
    }
</script>

<style scoped>
</style>
