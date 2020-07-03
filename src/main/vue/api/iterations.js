import axios from "axios";

export default {
    create(pollId, iterationCmd) {
        return axios.post("/api/v1/polls/" + pollId + "/iterations/", iterationCmd);
    },
    remove(pollId, iterationId) {
        return axios.delete("/api/v1/polls/" + pollId + "/iterations/" + iterationId);
    },
    update(pollId, iterationId, iterationCmd) {
        return axios.put(
            "/api/v1/polls/" + pollId + "/iterations/" + iterationId + "/",
            iterationCmd
        )
    }
}
