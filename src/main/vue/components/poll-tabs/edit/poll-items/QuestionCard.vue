<template>
    <b-card class="question-card" v-bind:class="{ 'question-card-hide-border': hideBorder} ">
        <div class="question-card-header">
            <div v-if="editable" class="float-right">
                <b-button-group sm>
                    <!-- edit button -->
                    <b-button variant="outline-secondary" @click="toggleEdit">
                        <b-icon-pencil />
                    </b-button>

                    <!-- grab handle -->
                    <b-button variant="outline-secondary">
                        <b-icon-arrow-up-down />
                    </b-button>
                </b-button-group>
            </div>

            <b-card-title v-if="model.type !== 'SectionHeaderModel'">{{ title }}</b-card-title>
            <h2 v-else>{{ title }}</h2>
        </div>

        <div class="question-card-user-area">
            <slot name="user-area"></slot>
        </div>

        <div class="question-card-meta-area" v-if="editing">
            <slot name="meta-area"></slot>
        </div>
    </b-card>
</template>

<script>
    export default {
        name: "QuestionCard",
        data() {
            return {
                editing: false
            }
        },
        props: {
            editable: {
                type: Boolean,
                required: false,
                default: true
            },
            title: {
                type: String,
            },
            hideBorder: {
                type: Boolean,
                required: false,
                default: false
            }
        },
        methods: {
            toggleEdit() {
                if (!this.editable) {
                    console.warn("Warning: toggling edit although this component is not editable. " +
                        "This should not happen.");
                }
                this.editing = !this.editing;
                if (!this.editing) {
                    this.$emit('editFinished');
                }
            }
        }
    }
</script>

<style lang="scss" scoped>
    @import '~bootstrap/scss/bootstrap.scss';
    .question-card {
        margin-bottom: 25px;
    }
    .question-card-user-area {
        margin-bottom: 10px;
    }
    .question-card-hide-border {
        border: 0;
    }
</style>
