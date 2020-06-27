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
    duplicate(pollId) {
        return axios.post('/api/v1/polls/' + pollId + '/')
    },
    update(pollCmd) {
        return axios.put('/api/v1/polls/' + pollCmd.id + '/', pollCmd);
    },
    addQuestion(pollId, questionCmd) {
        return axios.post('/api/v1/polls/' + pollId + '/questions/', questionCmd);
    },
    removeQuestion(pollId, questionId) {
        return axios.delete('/api/v1/polls/' + pollId + '/questions/' + questionId + '/');
    },
    updateQuestion(pollId, question) {
        return axios.put('/api/v1/polls/' + pollId + '/questions/' + question.id + '/', question);
    },
    updateStructure(pollId, structureCmd) {
        let cmd = {
            id: pollId,
            structure: {
                sectionToQuestions: {
                    ...structureCmd
                }
            }
        };
        return axios.put('/api/v1/polls/' + pollId + '/', cmd);
    },
    addPollSection(pollId, pollSectionCmd) {
        return axios.post('/api/v1/polls/' + pollId + '/sections/', pollSectionCmd);
    },
    updatePollSection(pollId, pollSectionCmd) {
        return axios.put('/api/v1/polls/' + pollId + '/sections/' + pollSectionCmd.id + '/', pollSectionCmd)
    },
    removePollSection(pollId, pollSectionId) {
        return axios.delete('/api/v1/polls/' + pollId + '/sections/' + pollSectionId + '/')
    },
    listParticipants(pollId) {
        return axios.get('/api/v1/polls/' + pollId + '/participants/');
    },
    addParticipant(pollId, participantCmd) {
        return axios.post('/api/v1/polls/' + pollId + '/participants/', participantCmd);
    },
    updateParticipant(pollId, participantCmd) {
      return axios.put('/api/v1/pools/' + pollId + '/participants/' + participantCmd.id + '/', participantCmd);
    },
    removeParticipant(pollId, participantId) {
        return axios.delete('/api/v1/polls/' + pollId + '/participants/' + participantId + '/');
    },
    delete(id) {
        return axios.delete('/api/v1/polls/' + id + '/')
    },
    listOwn(userId) {
        return axios.get('/api/v1/users/' + userId + '/ownPolls/')
    },
    listAssigned(userId) {
        return axios.get('/api/v1/users/' + userId + '/assignedPolls/')
    },
    download(cmd) {
        return axios({url: '/api/v1/download/' + cmd.id + '/' + cmd.type + '/' + cmd.format + '/',
            method: 'GET',
            responseType: 'blob'})
    }
}
