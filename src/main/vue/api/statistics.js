import axios from 'axios';

export default {
    getQuestion(pollID, questionID) {
        return axios.get('/api/v1/polls' + pollID + '/statistics/questions/' + questionID + '/')
    }
}