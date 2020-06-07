<template>
    <b-nav class="outline"
                  v-b-scrollspy="{offset: scrollspyOffset}">

        <b-nav-item v-for="item in pollStructure"
                           class="outline-item text-truncate"
                           v-bind:key="item.id"
                           v-bind:class="{ header: item.type === 'SectionHeader',
                                           question: item.type !== 'SectionHeader'}"
                           :href="'#pollItem-' + item.id"
                           @click="scrollTo('pollItem-' + item.id, $event)">
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
                default: 180,
                type: Number
            }
        },
        methods: {
            scrollTo(id, event) {
                event.preventDefault();
                let elem = document.getElementById(id);
                if (elem != null) {
                    let bounds = elem.getBoundingClientRect();
                    window.scrollTo({
                        left: 0,
                        top: bounds.y + window.scrollY - this.scrollspyOffset,
                        behavior: 'smooth'
                    });
                }
            }
        }
    }
</script>

<style lang="scss" scoped>
    @import "../assets/stylesheet.scss";

    .outline {

    }

    .outline-item {
        border: 0;
        padding: 0;
        margin-top: 0;
        margin-bottom: 5px;
    }

    .outline-item > a {
        color: $text-muted !important;
        background-color: transparent;
        padding: 0;
        margin: 0;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

    .outline-item.active > a {
        background-color: transparent;
        font-weight: bold !important;
    }

    .question {
        margin-left: 20px;
    }
</style>
