<template>
    <div>
        <input type="file" id="selectFiles" value="Import" /><br />
        <b-button class="mt-2" @click="readQuestionsFromFile">Import The File!</b-button>
    </div>
</template>

<script>
    export default {
        name: "UploadJSON",
        methods: {
            readQuestionsFromFile() {
                const files = document.getElementById('selectFiles').files;
                if (files.length <= 0) {
                    return false;
                }

                const fr = new FileReader();

                fr.onload = e => {
                    const result = JSON.parse(e.target.result);
                    this.$emit('uploadFinished', JSON.stringify(result, null, 2))
                };
                fr.readAsText(files.item(0));
            },
        }
    }
</script>

<style scoped>

</style>
