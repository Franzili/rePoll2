<template>
    <div style="text-align:center;">
        <p>{{item.question}}</p>

        <!-- all possible answers possibilities -->
        <div v-if="item.type === 'checkbox'">
            <b-form-group>
                <b-form-checkbox-group>
                    <div v-bind:key="pos.id" v-for="pos in item.possibilities">
                        <b-form-checkbox>{{pos.text}}</b-form-checkbox>
                    </div>
                </b-form-checkbox-group>
            </b-form-group>
        </div>

        <div v-if="item.type === 'radio'">
            <!-- TODO must one be selected? -->
            <b-form-group>
                <b-form-radio-group
                    :options="options"
                    plain
                    stacked
                    name="plain-stacked"
                ></b-form-radio-group>
            </b-form-group>
        </div>

        <div v-if="item.type === 'freetext'">
            <b-form-input class="text-box"/>
        </div>

        <div v-if="item.type === 'dropdown'">
            <b-dropdown class="drop-down" text="Dropdown Button">
                <div v-bind:key="pos.id" v-for="pos in item.possibilities">
                    <b-dropdown-item>{{pos.text}}</b-dropdown-item>
                </div>


            </b-dropdown>
        </div>


        <b-button @click="$emit('del-item', item.id)" variant="danger">delete</b-button>
        <p class="bottom-line"></p>
    </div>
</template>

<script>
    export default {
        name: "SurveyItem",
        data() {
            return {
                options: [
                    { text: 'very good'},
                    { text: 'good'},
                    { text: 'not good'}
                ]
            }
        },
        props: ["item"]
    }
</script>

<style scoped>

    .bottom-line {
        padding: 10px;
        border-bottom: 1px #000000 dotted;
    }

    .text-box {
        margin-bottom: 18px;
    }

    .drop-down {
        margin-bottom: 18px;
    }
</style>
