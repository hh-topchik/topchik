import { SHOW_ACTIVE_REPO } from './handlersActions';

export const activeRepositoryId = (state = '0', action) => {
    switch (action.type) {
        case SHOW_ACTIVE_REPO:
            return action.index;
        default:
            return state;
    }
};
