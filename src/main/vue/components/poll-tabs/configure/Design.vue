<template>
    <div>

        <b-card>
            <h6>Design</h6>


            <p style="font-family: AnjaliOldLipi">hallo</p>
                <b-card>
                    <b-row>
                        <b-col>

                            <!-- text colour -->
                            <h6>Text</h6>
                            <p>Colour:</p>
                            <b-form-input
                                v-model="selectedTextColour"
                                type="color">
                            </b-form-input>

                            <p></p>

                            <!-- font -->
                            <p>Font:</p>
                            <b-form-select :style="'font-family:' + this.selectedFont.valueOf()"
                                           v-model="selectedFont"
                                           :options="fonts">
                            </b-form-select>



                        </b-col>

                        <b-col>

                            <!-- background colour -->
                            <h6>Background</h6>
                            <p>Colour:</p>

                            <b-form-input
                                v-model="selectedBackgroundColour"
                                type="color">
                            </b-form-input>

                            <p></p>


                            <!-- Apply-Button -->
                            <b-button
                                style="margin-top: 30px"
                                class="float-right"
                                @click="saveDesign">
                                Apply
                            </b-button>

                        </b-col>

                    </b-row>

                    <p></p>


                    <!-- Preview -->
                    <h6>Preview</h6>

                    <b-card-group>
                        <b-card
                            :style="'background-color:' + this.poll.design.backgroundColour
                        + ';font-family:' + this.poll.design.font
                        + ';color:' + this.poll.design.textColour"
                            title="design of current poll">
                            <b-card-text>
                                This is the design of your poll.
                            </b-card-text>
                        </b-card>
                        <b-card
                            :style="'background-color:' + selectedBackgroundColour
                        + ';font-family:' + selectedFont
                        + ';color:' + selectedTextColour"
                            title="new design">
                            <b-card-text
                                :style="'color:' + selectedTextColour + ';' + selectedFont">
                                Are you sure you want to change it?
                            </b-card-text>
                        </b-card>
                    </b-card-group>





                </b-card>



            <p></p>


            <!-- Logo -->
            <b-card>
                <h6>Logo</h6>

                <b-row>
                    <b-col>
                        <!-- upload image -->
                        <b-form-file
                            no-drop
                            accept="image/*"
                            v-model="selectedFile"
                            placeholder="Choose a logo ..."
                            @change="onFileSelected"
                        ></b-form-file>

                        <p></p>


                        <!-- logo's position -->
                        <div v-if="fileBase64!==null && fileBase64!==''">
                            <h6>Position</h6>
                            <b-form-group>
                                <b-form-radio v-model="selectedPosition" value="left">left</b-form-radio>
                                <b-form-radio v-model="selectedPosition" value="center">center</b-form-radio>
                                <b-form-radio v-model="selectedPosition" value="right">right</b-form-radio>
                            </b-form-group>
                        </div>



                        <!-- Apply button -->
                        <b-button
                            style="margin-left: 20px"
                            class="float-right"
                            @click="saveLogo">Apply
                        </b-button>

                        <!-- delete logo button -->
                        <b-button
                            class="float-right"
                            @click="deleteLogo"
                            v-if="fileBase64!==null && fileBase64!==''"
                            variant="danger">
                            Delete logo
                        </b-button>


                    </b-col>


                    <b-col>
                        <b-card v-if="fileBase64==='' || fileBase64===null">
                            <p></p>
                            <p align="center">no logo uploaded</p>
                        </b-card>


                        <b-container
                            v-if="fileBase64!==''">
                            <b-img
                                center
                                height="200px"
                                alt="no logo uploaded"
                                :src="this.fileBase64">
                            </b-img>
                        </b-container>

                    </b-col>

                </b-row>


            </b-card>






        </b-card>
    </div>
</template>

<script>
    import {mapActions, mapState} from "vuex";

    export default {
        name: "Design",

        data () {
            return {
                colorInput: '',
                selectedFile: null,
                fileBase64: "",
                heightLogo: '220',
                selectedPosition: '',
                selectedTextColour: '',
                selectedBackgroundColour: '',
                selectedFont: '',
                fonts: [
                    { value: 'AnjaliOldLipi', text: 'AnjaliOldLipi' },
                    { value: 'Arial', text: 'Arial' },
                    { value: 'Chilanka', text: 'Chilanka' },
                    { value: 'Courier', text: 'Courier'},
                    { value: 'DejaVu', text: 'DejaVu'},
                    { value: 'DejaVu Math TeX Gyre', text: 'DejaVu Math TeX Gyre'},
                    { value: 'DejaVu Sans Mono', text: 'DejaVu Sans Mono' },
                    { value: 'DejaVu Sans Light', text: 'DejaVu Sans Light'},
                    { value: 'DejaVu Sans Serif', text: 'DejaVu Sans Serif'},
                    { value: 'DejaVu Serif Condensed', text: 'DejaVu Serif Condensed'},
                    { value: 'Georgia', text: 'Georgia'},
                    { value: 'Helvetica', text: 'Helvetica'},
                    { value: 'Latin Modern Math', text: 'Latin Modern Math'},
                    { value: 'Manjari Regular', text: 'Manjari Regular' },
                    { value: 'Monospaced', text: 'Monospaced' },
                    { value: 'Purisa', text: 'Purisa' },
                    { value: 'Ubuntu', text: 'Ubuntu'},
                    { value: 'Ubuntu Condensed', text: 'Ubuntu Condensed'},
                    { value: 'Ubuntu Light', text: 'UbuntuLight'},
                    { value: 'Ubuntu Mono', text: 'Ubuntu Mono'},
                    { value: 'SansSerif', text: 'SansSerif'},
                    { value: 'TeX Gyre Schola Math', text: 'TeX Gyre Schola Math'},
                    { value: 'Times', text: 'Times'},
                    { value: 'Times New Roman', text: 'Times New Roman'},
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
                let designCmd = {
                    logoPosition: this.selectedPosition,
                    logo: this.fileBase64
                };

                this.updateDesign({design: designCmd, pollId: this.poll.id})
            },
            deleteLogo() {
                this.selectedFile = null;
                this.fileBase64 = '';
                let designCmd = {
                    logo: ''
                };
                this.updateDesign({design: designCmd, pollId: this.poll.id})
            },
            async onFileSelected(event) {

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

                if (event.target.files[0] != null) {

                    this.selectedFile = event.target.files[0]

                    var promise = getBase64(this.selectedFile);

                    this.fileBase64 = await promise
                }
            },

            saveDesign() {
                let designCmd = {
                    font: this.selectedFont,
                    textColour: this.selectedTextColour,
                    backgroundColour: this.selectedBackgroundColour
                };
                this.updateDesign({design: designCmd, pollId: this.poll.id})
            }
        },
        created: function() {
            this.selectedFont = this.poll.design.font;
            this.selectedTextColour = this.poll.design.textColour;
            this.selectedBackgroundColour = this.poll.design.backgroundColour;
            this.selectedPosition = this.poll.design.logoPosition;
            this.fileBase64 = this.poll.design.logo
        }
    }
</script>

<style scoped>

</style>
