import axios from 'axios';

export default {
    list(id) {
        return axios.get('/api/v1/polls/' + id + '/statistics/')
    },
    byIteration(getCmd) {
        return axios.get('/api/v1/polls/' + getCmd.pollId + '/statistics/' + getCmd.iterationId + '/')
    },
    getPollAnswers(id) {
        return axios.get('/api/v1/polls/' + id + '/answers/')
    },
    getAnswersById(pollId, questionId) {
        return axios.get('/api/v1/polls/' + pollId + '/answers/' + questionId + '/');
    }
}
