import {
    FETCH_CONTRIBUTORS_SUCCESS,
    FETCH_CONTRIBUTOR_STATISTICS_SUCCESS,
} from './contributorsStatisticsActions';

export const contributors = (state = [], action) => {
    switch (action.type) {
        case FETCH_CONTRIBUTORS_SUCCESS:
            return [...state, action.contributors];
        default:
            return state;
    }
};

export const contributorsStatistics = (state = [], action) => {
    switch (action.type) {
        case FETCH_CONTRIBUTOR_STATISTICS_SUCCESS:
            return [...state, action.contributorStatistics];
        default:
            return state;
    }
};
