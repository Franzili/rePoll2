import axios from 'axios';

export default {
    login
}

function login(username, password) {
    const credentials = new URLSearchParams();
    credentials.append('username', username);
    credentials.append('password', password);
    return axios.post('/api/v1/authenticate', credentials);
}
