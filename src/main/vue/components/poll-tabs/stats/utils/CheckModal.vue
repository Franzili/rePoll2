<template>
    <b-modal
        scrollable
        hide-header-close
        id="edit-modal"
        title="Add or remove Questions:"
        ref="modal"
        header-border-variant="dark"
        footer-border-variant="dark"
        v-if="showModal"
        @ok="prevent"
        >
        <div class="d-block text-center">
            <b-container  v-bind:key="section.id" v-for="section in getPollStructure">
                <b-row>
                    <h5>Section: {{section.title}}</h5>
                </b-row>


                <b-form-checkbox-group v-model="selected">
                    <b-container v-bind:key="statistic.question.id" v-for="statistic in section.statistics">
                        <b-row>
                            <b-col cols="11">{{statistic.question.title}}</b-col>
                            <b-col cols="1">
                                <b-form-checkbox :value="statistic"></b-form-checkbox>
                            </b-col>
                        </b-row>
                    </b-container>
                </b-form-checkbox-group>
            </b-container>
        </div>
        <b-modal
            header-border-variant="dark"
            footer-border-variant="dark"
            button-size="sm"
            header-bg-variant="info"
            hide-header-close
            no-close-on-backdrop
            ok-only
            size="sm"
            id="warn-modal">
            For comparison purpose you will need at least two Questions.
        </b-modal>

        <div>Selected: <strong>{{ selected }}</strong></div>
    </b-modal>
</template>

<script>
    import {mapGetters} from "vuex";

    export default {
        name: "CheckModal",
        data() {
            return {
                showModal: true,
                selected: [],
            }
        },
        computed: {
            ...mapGetters('currentPoll', {
                getPollStructure: 'statStructureObj'
            })
        },
        methods: {
            show(list) {
                this.selected = list
                this.$refs.modal.show()
            },
            prevent(bvModalEvt) {
                bvModalEvt.preventDefault()
                this.handleSubmit()
            },
            handleSubmit() {
                if (this.selected.length < 2) {
                    this.$bvModal.show('warn-modal')
                    return
                }
                this.$emit('getSelected', this.selected)
                this.$nextTick(() => {
                    this.$bvModal.hide('edit-modal')
                })
            }
        }
    }
</script>

<style scoped>

</style>
