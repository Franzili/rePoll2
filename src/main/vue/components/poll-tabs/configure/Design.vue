<template>
    <div>

        <b-card>
            <h6>Design</h6>

            <!-- TODO height of v-app       style="max-height: 470px"-->


            <b-row>

                <b-col>
                    <b-card>

                        <!-- text colour -->
                        <h6>Text</h6>
                        <p>Colour:</p>
                        <b-form-input
                            v-model="this.selectedTextColour"
                            type="color">
                        </b-form-input>

                        <p></p>

                        <!-- font -->
                        <p>Font:</p>
                        <b-form-select :style="'font-family:' + this.selectedFont.valueOf()"
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

                        <b-form-input
                            v-model="selectedBackgroundColour"
                            type="color">
                        </b-form-input>

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



            <!-- Logo -->
            <b-card>
                <h6>Logo</h6>

                <b-row>
                    <b-col>
                        <!-- upload image -->
                        <b-form-file
                            accept="image/*"
                            v-model="selectedFile"
                            placeholder="Choose a logo or drop it here..."
                            drop-placeholder="Drop file here..."
                            @change="onFileSelected"
                        ></b-form-file>

                        <p></p>


                        <!-- logo's position -->
                        <div v-if="selectedFile!==null && selectedFile!==''">
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
                            <p align="center">no logo uploaded</p>
                        </b-card>

                        <b-card-img
                            v-if="fileBase64!==''"
                            :src="this.fileBase64">
                        </b-card-img>


                    </b-col>

                </b-row>


            </b-card>


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
                selectedPosition: '',
                selectedTextColour: '',
                selectedBackgroundColour: '',
                selectedFont: '',
                fonts: [
                    { value: 'Arial', text: 'Arial' },
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
                    { value: 'Monospaced', text: 'Monospaced' },
                    { value: 'Ubuntu', text: 'Ubuntu'},
                    { value: 'Ubuntu Condensed', text: 'Ubuntu Condensed'},
                    { value: 'Ubuntu Light', text: 'UbuntuLight'},
                    { value: 'Ubuntu Mono', text: 'Ubuntu Mono'},
                    { value: 'SansSerif', text: 'SansSerif'},
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
                this.selectedFile = '';
                this.fileBase64 = '';
                let designCmd = {
                    logo: ''
                };
                this.updateDesign({design: designCmd, pollId: this.poll.id})
            },
            async onFileSelected(event) {
                this.selectedFile = event.target.files[0]

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
