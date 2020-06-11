<template>
    <b-container>
        <!-- raw data for illustration
        <b-row>
            <p>
                {{statistic}}
            </p>
        </b-row>
        -->

        <b-row>
            <b-col cols="8">
                <h5>{{statistic.question.title}}</h5>
            </b-col>

            <b-col align-self="end" v-if="statistic.mode[0] !== undefined">
                <b-button-toolbar class="float-right">
                    <b-button-group class="mr-1">
                        <b-button title="Bar Chart" v-on:click="chartsObj.currentChart = 'bar'">
                            <b-icon icon="bar-chart-fill" aria-hidden="true"></b-icon>
                        </b-button>
                        <b-button title="Bar Chart" v-on:click="chartsObj.currentChart = 'donut'">
                            <b-icon icon="pie-chart-fill" aria-hidden="true"></b-icon>
                        </b-button>
                        <b-button v-if="this.statistic.question.type === 'ScaleQuestion'"
                            title="Boxplot"
                            v-on:click="chartsObj.currentChart = 'boxplot'">
                            <b-iconstack>
                                <b-icon stacked shift-h="10" icon="dash"></b-icon>
                                <b-icon stacked shift-h="" icon="vr"></b-icon>
                                <b-icon stacked shift-h="-10" icon="dash"></b-icon>
                            </b-iconstack>
                        </b-button>
                    </b-button-group>
                </b-button-toolbar>
            </b-col>

        </b-row>

        <b-row>
            <b-col v-if="statistic.mode[0] !== undefined">
                <p> mode: {{statistic.mode[0].text}}</p>
            </b-col>
        </b-row>


        <b-row v-if="statistic.question.type === 'TextQuestion'">
            <b-col md="3" offset-md="9">
                Answer count: 四百五十二<br>
                <b-button @click="$emit('changeTab', statistic.question.id)">Show Answers</b-button>
            </b-col>
        </b-row>

        <ChartsInlay v-bind:chartsObj="chartsObj"></ChartsInlay>

    </b-container>
</template>

<script>

    import ChartsInlay from "./ChartsInlay";

    export default {
        name: "ChartCards",
        props: ['statistic'],
        data() {
            return {
                chartsObj: {
                    data: [],
                    labels: [],
                    label: '',
                    qType: '',
                    currentChart: 'bar'
                },
                absFrq: [],
                //relFrq: []
            }
        },
        created() {
            let value = this.statistic
            this.doAmazingStuff(value);
            console.log('warum bin ich undefinded?', this.absFreq)
        },
        methods: {
            doAmazingStuff(val) {
                this.chartsObj.label = this.statistic.question.title;
                this.chartsObj.qType = this.statistic.question.type;
                for(let i = 0; i < val.frequencies.length; i++) {
                    this.chartsObj.labels.push(val.frequencies[i].choice.text);
                    //this.absFrq[i] = val.frequencies[i].absolute
                    this.chartsObj.data = [...this.chartsObj.data,val.frequencies[i].absolute]
                    //this.relFrq.push(frq[i].relative);
                }
                //this.chartsObj.data = this.absFrq
            },
        },
        components: {
            ChartsInlay
        }
    }
</script>

<style scoped>

</style>
