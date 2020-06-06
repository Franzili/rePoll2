import axios from 'axios';

export default {

    sendMailInvite(id) {
        return axios.get('/api/v1/admin/' + id + '/');
    }
}
