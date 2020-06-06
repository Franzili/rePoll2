import axios from 'axios';

export default {
    get(id) {
        return axios.get('/api/v1/polls/' + id + '/statistics/questions/')
    }
}