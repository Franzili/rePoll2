<template>
    <div>
        <input type="file" id="selectFiles" value="Import" /><br />
        <button @click="uploadFile">Import The File!</button>
        <p>{{test}}</p>
    </div>
</template>

<script>
    export default {
        name: "Upload",
        data() {
            return {
                test: ''
            }
        },
        methods: {
            uploadFile() {
                    const files = document.getElementById('selectFiles').files;
                    if (files.length <= 0) {
                        return false;
                    }

                    const fr = new FileReader();

                    fr.onload = e => {
                        const result = JSON.parse(e.target.result);
                        this.test = JSON.stringify(result, null, 2);
                    }
                    fr.readAsText(files.item(0));
            }
        }
    }
</script>

<style scoped>

</style>
