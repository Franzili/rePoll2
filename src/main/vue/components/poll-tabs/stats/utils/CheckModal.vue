<template>
    <b-modal
        scrollable
        title="Add or remove Questions:"
        ref="modal"
        header-border-variant="dark"
        footer-border-variant="dark"
        v-if="showModal"
        @ok="$emit('getSelected', selected)"
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
        created() {
            //console.log('im modal: ')
            //console.log(this.modalObj)
        },
        methods: {
            show(list) {
                this.selected = list
                this.$refs.modal.show()
            },
            hide() {
                this.$refs.modal.hide()
            },
        }
    }
</script>

<style scoped>

</style>
