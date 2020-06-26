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



            <!-- Logo -->
            <b-card>
                <h6>Logo</h6>

                <b-row>
                    <b-col>
                        <b-form-file
                            accept="image/*"
                            v-model="selectedFile"
                            placeholder="Choose a file or drop it here..."
                            drop-placeholder="Drop file here..."
                            @change="onFileSelected"
                        ></b-form-file>


                        <b-button @click="saveLogo">apply</b-button>


                    </b-col>

                    <b-col v-if="selectedFile!==null">
                        <b-card v-if="selectedFile===null">
                            <p align="center">no logo uploaded</p>

                        </b-card>


                        <b-card>
                            <b-img
                                height="200px"
                                width="200px"
                                :src="this.fileBase64"
                            ></b-img>

                        </b-card>

                        <!--<b-card-img :src="this.path">-->

                        <!--</b-card-img>-->

                    </b-col>

                </b-row>


            </b-card>












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








            <!-- Logo -->
            <div>










                <b-input type="color"></b-input>



            </div>


        </b-card>


    </div>
</template>

<script>
    import {mapActions, mapState} from "vuex";

    export default {
        name: "Design",

        data () {
            return {
                selectedFile: null, //weg?
                path: '',
                fileBase64: "hallo",

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
            saveLogo() {
                // TODO Speicherung im Backend


            },
            async onFileSelected(event) {
                this.selectedFile = event.target.files[0]
                this.path = URL.createObjectURL(event.target.files[0])




                function getBase64(file) {
                    return new Promise(function (resolve, reject) {
                        var reader = new FileReader();
                        reader.onload = function () {
                            resolve(reader.result);
                        };
                        reader.onerror = reject;
                        reader.readAsDataURL(file);
                    });
                }

                var promise = getBase64(this.selectedFile);
                promise.then(function (result) {
                    console.log(result);
                });

                this.fileBase64 = await promise
            },

            saveDesign() {
                let designCmd = {
                    font: this.selectedFont,
                    textColour: this.selectedTextColour,
                    backgroundColour: this.selectedBackgroundColour
                }
                this.updateDesign({design: designCmd, pollId: this.poll.id})
            }
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
