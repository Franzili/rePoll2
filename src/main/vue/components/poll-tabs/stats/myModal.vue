<template>
    <b-modal
        scrollable
        title="Add or remove Questions:"
        ref="modal"
        header-border-variant="dark"
        footer-border-variant="dark"
        v-if="showModal">
        <div class="d-block text-center">
            <b-container  v-bind:key="section.id" v-for="section in modalObj">
                <b-row>
                    <h5>Section: {{section.title}}</h5>
                </b-row>


                <b-form-checkbox-group v-model="selected">
                    <b-container v-bind:key="stat.question.id" v-for="stat in section.statistics">
                        <b-row>
                            <b-col cols="11">{{stat.question.title}}</b-col>
                            <b-col cols="1">
                                <b-form-checkbox :value="stat.question.id"></b-form-checkbox>
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
    export default {
        name: "myModal",
        props:['modalObj'],
        data() {
            return {
                showModal: true,
                selected: [],
            }
        },
        methods: {
            show() {
                this.$refs.modal.show()
            },
            hide() {
                this.$refs.modal.hide()
            }
        }
    }
</script>

<style scoped>

</style>
