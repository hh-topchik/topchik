export const SHOW_ACTIVE_REPO = 'SHOW_ACTIVE_REPO';
export const showActiveRepository = (index) => {
    return {
        type: SHOW_ACTIVE_REPO,
        index,
    };
};
