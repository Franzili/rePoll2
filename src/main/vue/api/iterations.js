import axios from 'axios';

export default {
    listAll(pollId) {
        return axios.get('/api/v1/polls/' + pollId + '/iterations/');
    },
    getIter(pollId, iterId) {
        return axios.get('api/v1/polls/' + pollId + '/iterations/' + iterId + '/');
    },
    addIter(pollId,iterationCmd) {
        return axios.post('/api/v1/polls/' + pollId + '/iterations/', iterationCmd);
    },
    removeIter(pollId, iterId) {
        return axios.delete('api/v1/polls/' + pollId + '/iterations/' + iterId + '/');
    },
    updateIter(pollId,id,  pollIterationCmd) {
        return axios.put('/api/v1/polls/' + pollId + '/iterations/' + id + '/', pollIterationCmd);
    }
}
