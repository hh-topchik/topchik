import {
    FETCH_DATA_SUCCESS,
    FETCH_DATA_REQUEST,
    FETCH_DATA_FAILURE,
    SHOW_ACTIVE_REPO,
    FETCH_REPOSITORY_TOPS_FAILURE,
    FETCH_REPOSITORY_TOPS_SUCCESS,
    FETCH_CONTRIBUTORS_SUCCESS,
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
        case FETCH_REPOSITORY_TOPS_SUCCESS:
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
        case FETCH_DATA_SUCCESS:
            return action.activeRepositoryId;
        default:
            return state;
    }
};

export const categories = (state = [], action) => {
    switch (action.type) {
        case FETCH_DATA_SUCCESS:
            return action.categories;
        case FETCH_DATA_REQUEST:
        case FETCH_DATA_FAILURE:
        default:
            return state;
    }
};

export const error = (state = [], action) => {
    switch (action.type) {
        case FETCH_DATA_FAILURE:
        case FETCH_REPOSITORY_TOPS_FAILURE:
            return action.error;
        default:
            return state;
    }
};

export const leaderboards = (state = [], action) => {
    switch (action.type) {
        case FETCH_DATA_SUCCESS:
        case FETCH_REPOSITORY_TOPS_SUCCESS:
            return [...state, ...action.leaderboards];
        default:
            return state;
    }
};

export const contributors = (state = [], action) => {
    switch (action.type) {
        case FETCH_CONTRIBUTORS_SUCCESS:
            return [...state, action.contributors];
        default:
            return state;
    }
};
