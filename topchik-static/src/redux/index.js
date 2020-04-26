import { combineReducers } from 'redux';
import {
    repositories,
    appStatus,
    activeRepositoryId,
    activeCategoryId,
} from './ranking/rankingReducers';

export default combineReducers({
    repositories,
    appStatus,
    activeRepositoryId,
    activeCategoryId,
});
