import { combineReducers } from 'redux';
import {
    repositories,
    appStatus,
    activeRepositoryId,
    categories,
    error,
    leaderboards,
} from './ranking/rankingReducers';

export default combineReducers({
    repositories,
    appStatus,
    activeRepositoryId,
    categories,
    error,
    leaderboards,
});
