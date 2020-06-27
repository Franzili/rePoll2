import axios from 'axios';

export default {
    updateConfigs(mailCmd) {
        return axios.post("/api/v1/configs/", mailCmd)
    },

    getConfigs() {
        return axios.get("/api/v1/configs/getMailConfigs/")
    }
}
