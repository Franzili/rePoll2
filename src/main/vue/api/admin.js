import axios from 'axios';

export default {
    updateConfigs(mailCmd) {
        return axios.post("/api/v1/configs/", mailCmd)
    }
}
