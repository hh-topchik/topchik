import { FETCH_CONTRIBUTOR_STATISTICS_SUCCESS } from './contributorsStatisticsActions';

export const contributorsStatistics = (state = [], action) => {
    switch (action.type) {
        case FETCH_CONTRIBUTOR_STATISTICS_SUCCESS:
            return [...state, action.contributorStatistics];
        default:
            return state;
    }
};
