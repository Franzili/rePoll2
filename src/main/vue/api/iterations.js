import axios from "axios";

export default {
    create(pollId, iterationCmd) {
        return axios.post("/api/v1/" + pollId + "/iterations/", iterationCmd);
    },
    update(pollId, iterationId, iterationCmd) {
        return axios.put(
            "/api/v1/" + pollId + "/iterations/" + iterationId + "/",
            iterationCmd
        )
    }
}
