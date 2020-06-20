import axios from 'axios';

export default {
    login,
    getRole
}

function login(username, password) {
    const credentials = new URLSearchParams();
    credentials.append('username', username);
    credentials.append('password', password);
    return axios.post('/api/v1/authenticate', credentials);
}

function getRole(username) {
    return axios.get('/api/v1/users/' + username + '/profile/')
}
