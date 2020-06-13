import axios from 'axios';

export default {
    get(id) {
        return axios.get('/api/v1/polls/' + id + '/statistics/')
    },
    getPollAnswers(id) {
        return axios.get('/api/v1/polls/' + id + '/answers/')
    },
    getAnswersById(pollId, questionId) {
        return axios.get('/api/v1/polls/' + pollId + '/answers/' + questionId + '/');
    }
}
