<template>
    <li>
        <b-card class="question-card"
                v-bind:class="{ 'question-card-hide-border': hideBorder || model.type === 'SectionHeader',
                                'question-card-is-header': model.type === 'SectionHeader' } ">

            <p class="question-card-header">
                <span class="question-card-title">
                    <EditableLabel v-if="model.type === 'SectionHeader'"
                                   tag="h2"
                                   :value="model.title"
                                   :editing="editing"
                                   v-on:valueChanged="model.title = $event"/>

                    <EditableLabel v-else
                                   tag="b-card-title"
                                   :value="model.title"
                                   :editing="editing"
                                   v-on:valueChanged="model.title = $event"/>
                </span>

                <span v-if="editable">
                    <b-button-group size="sm">
                        <!-- edit button -->
                        <b-button variant="outline-secondary" v-if="!editing"
                                                              @click="setEditing(true)">
                            <b-icon-pencil/>
                        </b-button>
                        <b-button variant="outline-secondary" v-else
                                                              @click="setEditing(false)">
                            <b-icon-check/>
                        </b-button>

                        <b-button variant="outline-secondary" @click="temporaryAlert()">
                            <b-icon-trash/>
                        </b-button>

                    </b-button-group>
                </span>

                <span v-if="editable">
                    <!-- grab handle -->
                    <b-button variant="outline-secondary" class="handle" size="sm">
                        <b-icon-arrow-up-down />
                    </b-button>
                </span>

            </p>


            <TextQuestion v-if="model.type === 'TextQuestion'"
                          :model="model"
                          :editing="editing"
                          :editable="editable"
                          class="question-body"
                          v-on:modelChanged="onModelChanged($event)"/>
            <SingleChoiceQuestion v-else-if="model.type === 'SingleChoiceQuestion'"
                                  :model="model"
                                  :editing="editing"
                                  :editable="editable"
                                  class="question-body"
                                  v-on:modelChanged="onModelChanged($event)"/>
            <MultiChoiceQuestion v-else-if="model.type === 'MultiChoiceQuestion'"
                                 :model="model"
                                 :editing="editing"
                                 :editable="editable"
                                 class="question-body"
                                 v-on:modelChanged="onModelChanged($event)"/>
            <ScaleQuestion v-else-if="model.type === 'ScaleQuestion'"
                           :model="model"
                           :editing="editing"
                           :editable="editable"
                           class="question-body"
                           v-on:modelChanged="onModelChanged($event)"/>
            <SectionHeader v-else-if="model.type === 'SectionHeader'"
                           class="question-card-content-area"
                           :model="model"
                           :editing="editing"
                           :editable="editable"
                           v-on:modelChanged="onModelChanged($event)"/>
        </b-card>
    </li>
</template>

<script>
    import SectionHeader from "./SectionHeader";
    import TextQuestion from "./TextQuestion";
    import SingleChoiceQuestion from "./SingleChoiceQuestion";
    import MultiChoiceQuestion from "./MultiChoiceQuestion";
    import ScaleQuestion from "./ScaleQuestion";

    import EditableLabel from "../../../EditableLabel";

    export default {
        name: "PollItem",
        data() {
            return {
                editing: false,
            }
        },
        props: {
            model: {
                type: Object,
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
            setEditing(editing) {
                if (!this.editable) {
                    console.warn("Warning: changing editing state although this component is not editable. " +
                        "This should not happen.");
                }
                this.editing = editing
                if (!this.editing) {
                    this.onEditFinished();
                } else {
                    this.onEditStarted();
                }
            },
            onEditFinished() {
                this.$emit('editFinished', this);
                console.debug(`[RePoll] Poll Item ${this.model.id} finished editing.`);
            },
            onEditStarted() {
                this.$emit('editStarted', this);
                console.debug(`[RePoll] Poll Item ${this.model.id} started editing.`);
            },
            temporaryAlert() {
                alert("This is to be implemented tonight! :)")
            }
        },
        components: {
            TextQuestion,
            MultiChoiceQuestion,
            SingleChoiceQuestion,
            ScaleQuestion,
            SectionHeader,
            EditableLabel,
        }
    }
</script>

<style lang="scss">
    .question-card {
        margin-bottom: 10px;
    }
    .question-card-header {
        display: flex;
    }
    .question-card-header > .question-card-title {
        flex-grow: 1;
    }
    .question-card-header > span:not(:first-child) {
        margin-left: 10px;
    }




    .question-card-hide-border {
        border: 0 !important;
    }
    .question-card-is-header {
        margin-top: 30px;
    }


    /* remove bottom margin from last <p> tag */
    .question-body > p:last-child {
        margin-bottom: 0;
    }
</style>
