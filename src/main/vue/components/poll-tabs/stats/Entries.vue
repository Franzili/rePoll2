<template>
    <div>
        <p> {{entries}} </p>
        <b-container>
            <b-col cols="8">
                <b-form-select v-model="selected" :options="this.structure">
                    <template v-slot:first>
                        <b-form-select-option :value="null">Select a User</b-form-select-option>
                    </template>
                </b-form-select>
            </b-col>
        </b-container>
    </div>
</template>

<script>
    import {mapState} from "vuex";

    export default {
        name: "Entries",
        data() {
            return {
                selected: null
            }
        },
        computed: {
            ...mapState('currentPoll', {
                poll: 'poll',
                pollAnswers: 'pollAnswers',
                entries: 'entries'
            })
        },
    }
</script>

<style scoped>

</style>
