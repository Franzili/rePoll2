import axios from 'axios';

export default {
    updateConfigs(mailCmd) {
        return axios.put("/api/v1/configs/", mailCmd)
    }
}
