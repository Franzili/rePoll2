import axios from 'axios';

export default {
    list() {
        return axios.get('/api/v1/users/');
    },
    get(id) {
        return axios.get('/api/v1/users/' + id + '/');
    },
    create(userCmd) {
        console.log(userCmd);
        return axios.post('/api/v1/users/', userCmd);
    },
    update(userCmd) {
        return axios.put('/api/v1/users/' + userCmd.id + '/', userCmd);
    },
    delete(id) {
        return axios.delete('/api/v1/users/' + id + '/')
    }
}
