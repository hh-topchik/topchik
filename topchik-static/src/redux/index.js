import { combineReducers } from 'redux';
import { repositories, appStatus, activeRepositoryId } from './ranking/rankingReducers';

export default combineReducers({
    repositories,
    appStatus,
    activeRepositoryId,
});
