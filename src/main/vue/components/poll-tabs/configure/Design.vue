<template>
    <div>
        <b-card>
            <h6>Design</h6>

            <p>{{this.selectedFont}}</p>

            <p :style=this.selectedFont.valueOf()>Font:</p>

            <b-form-select :style=this.selectedFont.valueOf() v-model="selectedFont" :options="fonts">
                <template v-slot:first>
                <!--    <b-form-select-option value=''>Arial</b-form-select-option>-->
                </template>
            </b-form-select>


        </b-card>
    </div>
</template>

<script>
    import {mapActions, mapState} from "vuex";

    export default {
        name: "Design",
        data () {
            return {
                selectedFont: '',
                fonts: [
                    { value: '', text: 'Arial' },
                    { value: 'font-family:Monospaced', text: 'Monospaced' }
                ]
            }
        },
        computed: {
            ...mapState('currentPoll', {
                poll: 'poll',
            })
        },
        methods: {
            ...mapActions('currentPoll', {
                updateDesign: 'updateDesign'
            }),
        },
        created: function() {
            this.selectedFont = this.poll.design.font;
        },
        watch: {
            selectedFont() {
                if (this.selectedFont !== this.poll.design.font) {
                    let designCmd = {
                        font: this.selectedFont
                    };
                    this.updateDesign({design: designCmd, pollId: this.poll.id})
                }
            }
        }
    }
</script>

<style scoped>

</style>
