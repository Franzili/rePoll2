import axios from 'axios';

export default {
    list() {
        return axios.get('/api/v1/polls/');
    },
    get(id) {
        return axios.get('/api/v1/polls/' + id);
    },
    save(poll) {
        let pollCmd = {title: poll.title, user: poll.user};
        return axios.post('/api/v1/polls/', pollCmd);
    },
    update(poll) {
        let pollCmd = {title: poll.title, user: poll.user};
        return axios.put('/api/v1/polls/' + poll.id, pollCmd);
    },
    listQuestions(pollID) { //list of questions in a given poll
        return axios.get('api/v1/polls/' + pollID + '/entries/')
    },
    getQuestion(pollID, id) { //from a given poll a specific question
        return axios.get('api/v1/polls/' + pollID + '/entries/' + id)
    },
    answerFirst(poll) { //saving first time answering the poll
        let pollEntryCmd = {pollID: poll.id , user: poll.user};
        return axios.post('api/v1/polls/' + poll.id + '/entries/', pollEntryCmd);
    },
    answerAgain(poll) { //saving every following time changing the answers
        let pollEntryCmd = {pollID: poll.id , user: poll.user};
        return axios.put('api/v1/polls/' + poll.id + '/entries/' + poll.id.entry.id , pollEntryCmd);
    }
}
