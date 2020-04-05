import { FETCH_DATA_SUCCESS, FETCH_DATA_REQUEST, FETCH_DATA_FAILURE } from './rankingActions';

export const repositories = (state = [], action) => {
    switch (action.type) {
        case FETCH_DATA_SUCCESS:
            return action.repositories;
        case FETCH_DATA_REQUEST:
        case FETCH_DATA_FAILURE:
        default:
            return state;
    }
};

export const appStatus = (state = 'not ready', action) => {
    switch (action.type) {
        case FETCH_DATA_SUCCESS:
        case FETCH_DATA_REQUEST:
        case FETCH_DATA_FAILURE:
            return action.appStatus;
        default:
            return state;
    }
};
