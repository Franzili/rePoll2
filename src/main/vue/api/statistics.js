import axios from 'axios';

export default {
    list(id) {
        return axios.get('/api/v1/polls/' + id + '/statistics/')
    },
    byIteration(getCmd) {
        return axios.get('/api/v1/polls/' + getCmd.pollId + '/statistics/' + getCmd.iterationId + '/')
    },
    getPollAnswers(getCmd) {
        return axios.get('/api/v1/polls/' + getCmd.pollId + '/iterations/' + getCmd.iterationId + '/answers/')
    },
    getAnswersById(getCmd) {
        return axios.get('/api/v1/polls/' + getCmd.pollId + '/iterations/' + getCmd.iterationId + '/answers/'
            + getCmd.questionId + '/');
    }
}
