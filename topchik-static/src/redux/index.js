import { combineReducers } from 'redux';
import {
    repositories,
    appStatus,
    activeRepositoryId,
    categories,
    error,
    leaderboards,
    contributors,
} from './ranking/rankingReducers';
import { contributorsStatistics } from './contributorsStatistics/contributorsStatisticsReducers';

export default combineReducers({
    repositories,
    appStatus,
    activeRepositoryId,
    categories,
    error,
    leaderboards,
    contributors,
    contributorsStatistics,
});
