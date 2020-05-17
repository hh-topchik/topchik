import axios from 'axios';
const qs = require('qs');
const HOST = 'http://localhost:8080/api';

export const FETCH_DATA_REQUEST = 'FETCH_DATA_REQUEST';
export const fetchRepositoriesAndCategoriesRequest = () => {
    return {
        type: FETCH_DATA_REQUEST,
        appStatus: 'not ready',
    };
};

export const FETCH_DATA_SUCCESS = 'FETCH_DATA_SUCCESS';
export const fetchRepositoriesAndCategoriesSuccess = (response) => {
    const globalTop = { id: 'global', title: 'Глобально' };
    return {
        type: FETCH_DATA_SUCCESS,
        repositories: [globalTop, ...response.data.repositories],
        appStatus: 'not ready',
        activeRepositoryId: 'global',
        categories: response.data.categories,
        leaderboards: [],
    };
};

export const FETCH_DATA_FAILURE = 'FETCH_DATA_FAILURE';
export const fetchRepositoriesAndCategoriesFailure = (error) => {
    return {
        type: FETCH_DATA_FAILURE,
        appStatus: 'fail',
        error: error,
    };
};

export const SHOW_ACTIVE_REPO = 'SHOW_ACTIVE_REPO';
export const showActiveRepository = (index) => {
    return {
        type: SHOW_ACTIVE_REPO,
        activeRepositoryId: index,
    };
};

export const FETCH_REPOSITORY_TOPS_SUCCESS = 'FETCH_REPOSITORY_TOPS_SUCCESS';
export const fetchRepositoryTopsSuccess = (response, repositoryId, period) => {
    const leaderboards = response.data.map((category) => {
        return {
            repositoryId: repositoryId,
            categoryId: category.categoryId,
            period: period,
            top: category.top,
        };
    });

    return {
        type: FETCH_REPOSITORY_TOPS_SUCCESS,
        leaderboards: leaderboards,
        appStatus: 'ready',
    };
};

export const FETCH_REPOSITORY_TOPS_FAILURE = 'FETCH_REPOSITORY_TOPS_FAILURE';
export const fetchRepositoryTopsFailure = (error) => {
    return {
        type: FETCH_REPOSITORY_TOPS_FAILURE,
        appStatus: 'fail',
        error: error,
    };
};

export const fetchRepositoriesAndCategories = () => (dispatch) => {
    dispatch(fetchRepositoriesAndCategoriesRequest());

    axios
        .get(HOST + '/reposAndCategories')
        .then((response) => {
            return new Promise((resolve, reject) => resolve(response));
        })
        .then((response) => {
            dispatch(fetchRepositoriesAndCategoriesSuccess(response));
            const repositoryId = 'global';
            const categories = response.data.categories;
            axios
                .get(HOST + '/globalTops/', {
                    params: {
                        repoId: repositoryId,
                        categoryId: categories.map((category) => category.id),
                        period: 'year',
                    },
                    paramsSerializer: (params) => {
                        return qs.stringify(params, { arrayFormat: 'repeat' });
                    },
                })
                .then((response) => {
                    dispatch(fetchRepositoryTopsSuccess(response, repositoryId, 'week'));
                })
                .catch((error) => {
                    dispatch(fetchRepositoryTopsFailure(error));
                });
        })
        .catch((error) => {
            dispatch(fetchRepositoriesAndCategoriesFailure(error));
        });
};

export const fetchCategoryTopForPeriod = (url, repositoryId, categories, period) => (dispatch) => {
    axios
        .get(HOST + url, {
            params: {
                repoId: repositoryId,
                categoryId: categories.map((category) => category.id),
                period: period,
            },
            paramsSerializer: (params) => {
                return qs.stringify(params, { arrayFormat: 'repeat' });
            },
        })
        .then((response) => {
            dispatch(fetchRepositoryTopsSuccess(response, repositoryId, period));
        })
        .catch((error) => {
            dispatch(fetchRepositoryTopsFailure(error));
        });
};
