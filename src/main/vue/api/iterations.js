import axios from "axios";

export default {
    create(pollId, iterationCmd) {
        return axios.post("/api/v1/" + pollId + "/iterations/", iterationCmd);
    }
}
