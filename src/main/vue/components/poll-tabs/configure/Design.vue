<template>
    <div>

        <b-card style="background-color:white">
            <h6>Design</h6>

            <!-- TODO heigh of v-app -->
            <v-app
                style="max-height: 470px">
                <v-main>

                    <v-row>
                        <b-col>
                            <b-col>
                                <!-- text colour -->
                                <p>Text-colour:</p>
                                <div align="left">
                                    <v-color-picker
                                        elevation="15"
                                        v-model="selectedTextColour"
                                        mode="hexa">
                                    </v-color-picker>
                                </div>
                            </b-col>

                            <b-col>
                                <!-- font -->
                                <p :style=this.selectedFont.valueOf()>Font:</p>
                                <b-form-select :style=this.selectedFont.valueOf()
                                               v-model="selectedFont"
                                               :options="fonts">
                                    <template v-slot:first>
                                        <!--    <b-form-select-option value=''>Arial</b-form-select-option>-->
                                    </template>
                                </b-form-select>
                            </b-col>
                        </b-col>

                        <b-col>
                            <b-col>
                                <!-- background colour -->
                                <p>Background-colour:</p>
                                <div align="left">
                                    <v-color-picker
                                        elevation="15"
                                        v-model="selectedBackgroundColour"
                                        mode="hexa">
                                    </v-color-picker>
                                </div>
                            </b-col>
                        </b-col>

                    </v-row>

                </v-main>

            </v-app>

            <p style="background-color:white">{{this.selectedFont}}</p>
            <p :style="'color:' + selectedTextColour + ';'
                + selectedFont">{{this.selectedTextColour}}</p>
        </b-card>
    </div>
</template>

<script>
    import {mapActions, mapState} from "vuex";

    export default {
        name: "Design",
        data () {
            return {
                selectedTextColour: '',
                selectedBackgroundColour: '',

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
