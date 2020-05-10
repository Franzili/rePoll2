<template>
    <div>
        <h1>Survey ausfüllen</h1>
        <div>
            Titel: <input type="text" v-model="survey.title">
        </div>
        <div>
            Text: <textarea v-model="survey.text" rows="7"></textarea>
        </div>
        <button @click="save">Speichern</button>
    </div>
</template>

<script>
    import {mapGetters, mapMutations} from "vuex";

    export default {
        name: "Answer",
        data() {
            return {
                id: 0,
                article: {}
            }
        },
        created() {
            this.id = this.$route.params.id
            this.survey = this.getSurvey(this.id)
        },
        computed: {
            ...mapGetters(['getSurvey']),
        },
        methods: {
            ...mapMutations([
                'updateSurvey'
            ]),
            answer() {
                this.updateSurvey(this.survey)
                this.$router.push('/')
            }
        }
    }
</script>

<style scoped>

    .my-row {
        padding: 20px;
    }
</style>
