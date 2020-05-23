import axios from 'axios';

export default {
    list() {
        return axios.get('/api/v1/polls/');
    },
    get(id) {
        return axios.get('/api/v1/polls/' + id);
    },
    save(poll) {
        let pollCmd = {title: poll.title, user: poll.user};
        return axios.post('/api/v1/polls/', pollCmd);
    },
    update(poll) {
        let pollCmd = {title: poll.title, user: poll.user};
        return axios.put('/api/v1/polls/' + poll.id, pollCmd);
    }
}
