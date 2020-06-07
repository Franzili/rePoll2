import axios from 'axios';

export default {
    list() {
        return axios.get('/api/v1/polls/');
    },
    get(id) {
        return axios.get('/api/v1/polls/' + id + '/');
    },
    create(poll) {
        let pollCmd = {
            title: poll.title
        };
        return axios.post('/api/v1/polls/', pollCmd);
    },
    update(pollCmd) {
        return axios.put('/api/v1/polls/' + pollCmd.id + '/', pollCmd);
    },
    updatePollSection(pollId, pollSectionCmd) {
        return axios.put('/api/v1/polls/' + pollId + '/sections/' + pollSectionCmd.id + '/', pollSectionCmd)
    },
    delete(id) {
        return axios.delete('/api/v1/polls/' + id + '/')
    },
    listOwn(userId) { //(userId) //()
    return axios.get('api/v1/users/' + userId + '/ownPolls/') //' + userId + ' //{userId}
        },
    listAssigned() {
        return axios.get('api/v1/users/{userId}/assignedPolls/')
    }
}
