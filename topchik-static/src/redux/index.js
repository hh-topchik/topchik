import { combineReducers } from 'redux';
import {
    repositories,
    appStatus,
    activeRepositoryId,
    categories,
    error,
    leaderboards,
} from './ranking/rankingReducers';
import {
    contributors,
    contributorsStatistics,
} from './contributorsStatistics/contributorsStatisticsReducers';

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
