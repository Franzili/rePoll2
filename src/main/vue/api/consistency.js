import axios from 'axios'

export default {
    list(pollId) {
        return axios.get('/api/v1/polls/' + pollId + '/consistency/')
    },
    add(addCmd) {
        return axios.post('/api/v1/polls/' + addCmd.pollId + '/consistency/', addCmd.consistencyCmd)
    },
    update(updCmd) {
        return axios.put('/api/v1/polls/' + updCmd.pollId + '/consistency/' + updCmd.constId + '/',
            updCmd.consistencyCmd)
    },
    delete(dltCmd) {
        return axios.delete('/api/v1/polls/' + dltCmd.pollId + '/consistency/' + dltCmd.constId + '/')
    }

}
