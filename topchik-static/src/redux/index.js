import { combineReducers } from 'redux';
import { repositories, appStatus } from './ranking/rankingReducers';
import { activeRepositoryId } from './handlers/handlersReducers';

export default combineReducers({
    repositories,
    appStatus,
    activeRepositoryId,
});
