import axios from 'axios';

export default {
    list(getCmd) {
        return axios.get('/api/v1/polls/' + getCmd.pollId +'/iterations/' + getCmd.iterationId+'/entries/');
    }
}
