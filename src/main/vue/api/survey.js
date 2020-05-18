import axios from 'axios';

export default {
    list() {
        return axios.get('/api/v1/polls/');
    },
    get(id) {
        return axios.get('/api/v1/polls/' + id);
    }
}
