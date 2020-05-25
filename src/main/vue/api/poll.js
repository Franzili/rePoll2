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
    }
    /*,
    answerFirst(poll) { //first time answering the poll
        let pollEntryCmd = {pollID: poll.id , user: poll.user};
        return axios.post('api/v1/polls/' + poll.id + '/entries/', pollEntryCmd);
    },
    answerAgain(poll) { //every following time changing the answers
        let pollEntryCmd = {x , user: poll.user};
        return axios.put('api/v1/polls/' + poll.id + '/entries/' + poll.id.entry.id , pollEntryCmd);
    }*/
}
