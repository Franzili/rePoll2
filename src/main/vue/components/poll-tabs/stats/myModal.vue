<template>
    <b-modal scrollable ref="modal" v-if="showModal">
        <div class="d-block text-center">
            <b-container  v-bind:key="section.section.id" v-for="section in modalObj">
                <b-row>
                    <h5>Section: {{section.section.name}}</h5>
                </b-row>


                <b-form-checkbox-group v-model="selected">
                    <b-container v-bind:key="question.value" v-for="question in section.questions">
                        <b-row>
                            <b-col cols="8">{{question.text}}</b-col>
                            <b-col cols="1">
                                <b-form-checkbox :value="question.value"></b-form-checkbox>
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
