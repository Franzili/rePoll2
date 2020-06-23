import axios from 'axios';

export default {
    updateDesign(designUpdate) {
        return axios.put('/api/v1/polls/' + designUpdate.pollId + '/design/', designUpdate.design);
    }
}
