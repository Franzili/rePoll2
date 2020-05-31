<template>
    <b-card>
        <h6>Status</h6>

        <b-row class="align-items-center">
            <b-col>
                <b-form-radio-group
                    v-model="status"
                    name="plain-inline">
                    <b-form-radio value="IN_PROCESS" >In Bearbeitung</b-form-radio>
                    <b-form-radio value="READY">Bereit</b-form-radio>
                    <b-form-radio value="ACTIVATED">Aktiviert</b-form-radio>
                    <b-form-radio value="DEACTIVATED">Deaktiviert</b-form-radio>
                </b-form-radio-group>
            </b-col>

            <b-col cols="2">
                <b-button class="float-right" >Apply</b-button>
            </b-col>
        </b-row>
    </b-card>
</template>

<script>
    export default {
        name: "SelectStatus",
        data() {
            return {
                status: ""
            }
        }
    }
</script>

<style scoped>

</style>
