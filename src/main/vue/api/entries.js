import axios from 'axios';

export default {
    list(id) {
        return axios.get('/api/v1/polls/' + id + '/entries/');
    }
}
