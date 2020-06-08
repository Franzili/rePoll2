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
        }
        return axios.put('/api/v1/polls/' + pollId + '/', cmd);
    },
    updatePollSection(pollId, pollSectionCmd) {
        return axios.put('/api/v1/polls/' + pollId + '/sections/' + pollSectionCmd.id + '/', pollSectionCmd)
    },
    delete(id) {
        return axios.delete('/api/v1/polls/' + id + '/')
    }
}
