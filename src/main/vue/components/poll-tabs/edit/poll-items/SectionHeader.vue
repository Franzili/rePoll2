<template>
    <div
        :style="'font-family:' + poll.design.font + ';color:' + poll.design.textColour">
        <EditableLabel :editing="editing"
                       tag="span"
                       :value="model.description"
                       v-on:valueChanged="model.description = $event"
                       class="text-muted"/>
    </div>
</template>

<script>
    import EditableLabel from "../../../EditableLabel";
    import {mapState} from "vuex";

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
                    this.$emit('modelChanged', this.model)
                },
                deep: true
            }
        },
        components: {
            EditableLabel
        },
        computed: {
            ...mapState('currentPoll', {
                poll: 'poll',
            })
        }
    }
</script>

<style scoped>
</style>
