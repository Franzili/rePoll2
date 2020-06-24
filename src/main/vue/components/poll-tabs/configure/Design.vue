<template>
    <div>

        <b-card>
            <h6>Design</h6>

            <!-- TODO height of v-app       style="max-height: 470px"-->

            <v-app>
                <v-main>

                    <b-row>

                        <b-col>
                            <b-card>

                                <!-- text colour -->
                                <h6>Text</h6>
                                <p>Colour:</p>
                                <div align="center">
                                    <!-- elevation = "15" -->
                                    <v-color-picker app
                                                    v-model="selectedTextColour"
                                                    mode="hexa">
                                    </v-color-picker>
                                </div>

                                <!-- font -->
                                <p :style=this.selectedFont.valueOf()>Font:</p>
                                <b-form-select :style=this.selectedFont.valueOf()
                                               v-model="selectedFont"
                                               :options="fonts">
                                    <!-- <template v-slot:first> -->
                                    <!--    <b-form-select-option value=''>Arial</b-form-select-option>-->
                                    <!--  </template> -->
                                </b-form-select>

                            </b-card>

                        </b-col>

                        <b-col>
                            <b-card>

                                <!-- background colour -->
                                <h6>Background</h6>
                                <p>Colour:</p>
                                <!-- elevation="15" -->

                                <div align="center">
                                    <v-color-picker app
                                                    v-model="selectedBackgroundColour"
                                                    mode="hexa">
                                    </v-color-picker>
                                </div>

                            </b-card>

                            <!-- Apply-Button -->
                            <div>
                                <b-button
                                    style="margin-top: 30px"
                                    class="float-right"
                                    @click="saveDesign">
                                    Apply
                                </b-button>
                            </div>
                        </b-col>
                    </b-row>

                </v-main>
            </v-app>


            <h6>Preview</h6>

            <b-card-group>
                <b-card
                    :style="'background-color:' + this.poll.design.backgroundColour + ';'
                        + this.poll.design.font
                        + ';color:' + this.poll.design.textColour"
                    title="design of current poll">
                    <b-card-text>
                        This is the design of your poll.
                    </b-card-text>
                </b-card>
                <b-card
                    :style="'background-color:' + selectedBackgroundColour + ';'
                        + selectedFont
                        + ';color:' + selectedTextColour"
                    title="new design">
                    <b-card-text
                        :style="'color:' + selectedTextColour + ';' + selectedFont">
                    Are you sure you want to change it?
                    </b-card-text>
                </b-card>
            </b-card-group>



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
            saveDesign() {
                let designCmd = {
                    font: this.selectedFont,
                    textColour: this.selectedTextColour,
                    backgroundColour: this.selectedBackgroundColour
                }
                this.updateDesign({design: designCmd, pollId: this.poll.id})            }
        },
        created: function() {
            this.selectedFont = this.poll.design.font;
            this.selectedTextColour = this.poll.design.textColour;
            this.selectedBackgroundColour = this.poll.design.backgroundColour
        }
    }
</script>

<style scoped>

</style>
