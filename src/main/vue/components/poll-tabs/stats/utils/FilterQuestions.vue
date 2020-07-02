<template>
    <b-row>

        <b-col lg="6" class="my-1">
            <b-form-group
                label="Filter"
                label-cols-sm="3"
                label-align-sm="right"
                label-size="sm"
                label-for="filterInput"
                class="mb-0"
            >
                <b-input-group size="sm">
                    <b-form-input
                        v-model="filter"
                        type="search"
                        id="filterInput"
                        placeholder="Type to Search"
                    ></b-form-input>
                    <b-input-group-append>
                        <b-button :disabled="!filter" @click="filter = ''">Clear</b-button>
                    </b-input-group-append>
                </b-input-group>
            </b-form-group>
        </b-col>

        <b-col lg="6" class="my-1">
            <b-form-group
                label="Filter On"
                label-cols-sm="3"
                label-align-sm="right"
                label-size="sm"
                class="mb-0">
                <b-form-checkbox-group v-model="filterOn" class="mt-1">
                    <b-form-checkbox value="Username">Username</b-form-checkbox>
                    <b-form-checkbox value="Answers">Answers</b-form-checkbox>
                </b-form-checkbox-group>
            </b-form-group>
        </b-col>
    </b-row>
</template>

<script>

    import Questions from "../Questions";

    export default {
        name: "FilterQuestions",
        data() {

            return {
                totalRows: 1,
                filter: '',
                filterOn: [],
                sortDesc: false,
                sortDirection: 'asc',
            }
        },
        mounted() {
            // Set the initial number of items
            this.totalRows = Questions.data().answerSet.length
        },
        methods: {
            onFiltered(filteredItems) {
                this.totalRows = filteredItems.length
            }
        }

    }
</script>

<style scoped>

</style>
