<template>
    <b-nav class="outline"
                  v-b-scrollspy="{offset: scrollspyOffset}">

        <b-nav-item v-for="item in pollStructure"
                           class="outline-item text-truncate"
                           active-class="outline-item-active"
                           v-bind:key="item.id"
                           v-bind:class="{ header: item.type === 'SectionHeaderModel',
                                           question: item.type !== 'SectionHeaderModel'}"
                           :href="'#pollItem-' + item.id.toString()">
            {{ item.title }}
        </b-nav-item>
    </b-nav>
</template>

<script>
    export default {
        name: "Outline",
        props: {
            pollStructure: {
                required: true,
                type: Array
            },
            scrollspyOffset: {
                required: false,
                default: 400,
                type: Number
            }
        }
    }
</script>

<style lang="scss" scoped>
    @import "../assets/stylesheet.scss";

    .outline {

    }

    .outline-item {
        color: $text-muted !important;
        background-color: transparent;
        border: 0;
        padding: 0;
        margin-top: 0;
        margin-bottom: 5px;
    }

    .outline-item-active {
        background-color: transparent;
        color: black !important;
        font-weight: bold !important;
    }

    .question, .header {
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

    .question {
        margin-left: 20px;
    }
</style>
