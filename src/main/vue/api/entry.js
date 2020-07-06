import axios from "axios";


export default {
    answerFirst(entrysOfPoll) { //saving first time answering the poll
        return axios.post(
            '/api/v1/polls/' + entrysOfPoll.pollId + '/entries/',
            {
                answers: entrysOfPoll.entryCmd,
                participantID: entrysOfPoll.participantId
            }
        );
    },
    //answerAgain(entrysOfPoll) { //saving every following time changing the answers
    //    return axios.put('/api/v1/polls/' + entrysOfPoll.pollId + '/entries/' +// entryId + '/', pollEntryCmd);
    //}
}
