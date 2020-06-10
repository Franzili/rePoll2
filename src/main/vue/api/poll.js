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
    answerFirst(pollId, pollEntryCmd, userId) { //saving first time answering the poll
        return axios.post('api/v1/polls/' + pollId + '/entries/' + userId + '/', pollEntryCmd);
    },
    answerAgain(pollId, pollEntryCmd, userId) { //saving every following time changing the answers
        return axios.put('api/v1/polls/' + pollId + '/entries/' + userId + '/', pollEntryCmd);
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
    listOwn(userId) {
    return axios.get('api/v1/users/' + userId + '/ownPolls/')
        },
    listAssigned(userId) {
        return axios.get('api/v1/users/' + userId + '/assignedPolls/')
    }
}
