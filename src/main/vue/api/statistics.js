import axios from 'axios';

export default {
    getAnswers(pollId, questionId) {
        return axios.get('/api/v1/polls/' + pollId + '/statistics/questions/' + questionId + '/answers/');
    }
}
