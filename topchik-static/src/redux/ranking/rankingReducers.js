import {
    FETCH_DATA_SUCCESS,
    FETCH_DATA_REQUEST,
    FETCH_DATA_FAILURE,
    SHOW_ACTIVE_REPO,
    SHOW_ACTIVE_CATEGORY,
} from './rankingActions';

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

export const activeRepositoryId = (state = '0', action) => {
    switch (action.type) {
        case SHOW_ACTIVE_REPO:
            return action.activeRepositoryId;
        case FETCH_DATA_SUCCESS:
            return action.activeRepositoryId;
        default:
            return state;
    }
};
