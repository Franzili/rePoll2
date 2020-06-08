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
    answerFirst(poll) { //saving first time answering the poll
        let pollEntryCmd = {pollID: poll.id , user: poll.user};
        return axios.post('api/v1/polls/' + poll.id + '/entries/', pollEntryCmd);
    },
    answerAgain(poll) { //saving every following time changing the answers
        let pollEntryCmd = {pollID: poll.id, user: poll.user};
        return axios.put('api/v1/polls/' + poll.id + '/entries/' + poll.id.entry.id, pollEntryCmd);
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
