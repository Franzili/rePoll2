import axios from "axios";

axios.interceptors.request.use(
    function(config) {
        console.debug(`[RePoll] Sending ${config.method} request for URL ${config.url}`)
        return config;
    },
    function(error) {
        console.warn(`[RePoll] Request error: ${error}`)
        return Promise.reject(error);
    }
);

