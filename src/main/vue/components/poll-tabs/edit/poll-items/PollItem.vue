<template>
    <li>
        <b-card class="question-card"
                v-bind:class="{ 'question-card-hide-border': hideBorder || model.type === 'SectionHeaderModel',
                                'question-card-is-header': model.type === 'SectionHeaderModel' } ">
            <div class="question-card-header">
                <div v-if="editable" class="float-right">
                    <b-button-group sm>
                        <!-- edit button -->
                        <b-button variant="outline-secondary" @click="toggleEdit">
                            <b-icon-pencil v-if="!editing" />
                            <b-icon-check v-else />
                        </b-button>

                        <!-- grab handle -->
                        <b-button variant="outline-secondary">
                            <b-icon-arrow-up-down />
                        </b-button>
                    </b-button-group>
                </div>

                <EditableLabel v-if="model.type === 'SectionHeaderModel'"
                               tag="h2"
                               :value="model.title"
                               :editing="editing"
                               v-on:valueChanged="model.title = $event"/>

                <EditableLabel v-else
                               tag="b-card-title"
                               :value="model.title"
                               :editing="editing"
                               v-on:valueChanged="model.title = $event"/>
            </div>

            <TextQuestion v-if="tmpModel.type === 'TextQuestionModel'"
                          :model="model"
                          :editing="editing"
                          v-on:modelChanged="onModelChanged($event)"/>
            <SectionHeader v-if="tmpModel.type === 'SectionHeaderModel'"
                           :model="model"
                           :editing="editing"
                           v-on:modelChanged="onModelChanged($event)"/>
        </b-card>
    </li>
</template>

<script>
    import PollItemModel from "../../../../store/poll-item-models/PollItemModel";

    import SectionHeader from "./SectionHeader";
    import TextQuestion from "./TextQuestion";
    import EditableLabel from "../../../EditableLabel";

    export default {
        name: "PollItem",
        data() {
            return {
                editing: false,
                tmpModel: this.model
            }
        },
        props: {
            model: {
                type: PollItemModel,
                required: true
            },
            editable: {
                type: Boolean,
                required: false,
                default: true
            },
            hideBorder: {
                type: Boolean,
                required: false,
                default: false
            }
        },
        methods: {
            onModelChanged(newModel) {
                this.model = newModel
            },
            toggleEdit() {
                if (!this.editable) {
                    console.warn("Warning: toggling edit although this component is not editable. " +
                        "This should not happen.");
                }
                this.editing = !this.editing;
                if (!this.editing) {
                    this.onEditFinished()
                }
            },
            onEditFinished() {
                console.log("Edit finished!");
            }
        },
        components: {EditableLabel, SectionHeader, TextQuestion}
    }
</script>

<style lang="scss" scoped>
    .question-card {
        margin-bottom: 10px;
    }
    .question-card-hide-border {
        border: 0;
    }
    .question-card-is-header {
        margin-top: 20px;
    }
</style>
