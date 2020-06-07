import axios from 'axios';

export default {

    sendMailInvite(userName) {
        return axios.get('/api/v1/admin/newUser/' + userName + '/');
    }
}
