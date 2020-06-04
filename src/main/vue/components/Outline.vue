<template>
    <div>
        <div v-for="item in pollStructure" v-bind:key="item.key" class="outline-item text-truncate">
            <b v-if="item.type === 'SectionHeaderModel'" class="header text-truncate">
                {{item.title}}
            </b>
            <span v-else class="question">
                {{item.title}}
            </span>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Outline",
        props: {
            pollStructure: {
                required: true,
                type: Array
            }
        }
    }
</script>

<style lang="scss" scoped>
    @import "../assets/stylesheet.scss";

    .outline-item {
        overflow: hidden;
    }

    .question, .header {
        color: $text-muted;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

    .question {
        margin-left: 20px;
    }
</style>
