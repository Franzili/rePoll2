import axios from 'axios';

export default {
    list() {
        return axios.get('/api/v1/polls/');
    },
    get(id) {
        return axios.get('/api/v1/polls/' + id);
    },
    save(survey) {
        let pollCmd = {title: survey.title, user: survey.user};
        return axios.post('/api/v1/polls/', pollCmd);
    },
    update(survey) {
        let pollCmd = {title: survey.title, user: survey.user};
        return axios.put('/api/v1/polls/' + survey.id, pollCmd);
    }
}
