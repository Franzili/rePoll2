<template>
    <div>
        <b-card>
            <h6>Design</h6>

            <p>Font:</p>

            <b-form-select :style=this.selected.valueOf() v-model="selected" :options="fonts">
                <template v-slot:first>
                    <b-form-select-option value=''>Arial</b-form-select-option>
                </template>
            </b-form-select>


        </b-card>
    </div>
</template>

<script>
    export default {
        name: "Design",
        data () {
            return {
                selected: '',
                fonts: [
                    { value: 'font-family:Monospaced', text: 'Monospaced' }
                ]
            }
        }
    }
</script>

<style scoped>

</style>
