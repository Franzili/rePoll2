import axios from 'axios';

export default {
    get(id) {
        return axios.get('/api/v1/polls/' + id + '/statistics/questions/')
    }
}
    getAnswers(pollId, questionId) {
        return axios.get('/api/v1/polls/' + pollId + '/statistics/questions/' + questionId + '/answers/');
    }
}
