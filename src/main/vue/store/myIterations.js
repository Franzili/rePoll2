import api from "../api";

const iterations = {
    state: {
        iterations: []
    },
    getters: {

    },
    mutations: {
        set(state, newIterations) {
            state.iterations = newIterations;
        },
        update(state, iterationCmd) {
            let IndexOldIteration = state.iterations.findIndex(oldIteration =>
                oldIteration.id === iterations.id);
            state.iterations[IndexOldIteration] = iterationCmd;
        },
        delete(state, id) {
            state.participants.filter(participants => participants.id !== id);
        }
    }
}
