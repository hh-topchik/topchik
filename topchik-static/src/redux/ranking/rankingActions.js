import axios from 'axios';
import uuid from 'uuid/v4';

/* Работа с сервером */
export const FETCH_DATA_REQUEST = 'FETCH_DATA_REQUEST';
export const fetchRankingRequest = () => {
    return {
        type: FETCH_DATA_REQUEST,
        appStatus: 'not ready',
    };
};

export const FETCH_DATA_SUCCESS = 'FETCH_DATA_SUCCESS';
export const fetchRankingSuccess = (response) => {
    const repositories = response.data.repositories.map((repository) => {
        return {
            id: uuid(),
            name: repository.title,
            categories: repository.sections,
        };
    });

    return {
        type: FETCH_DATA_SUCCESS,
        repositories: repositories,
        appStatus: 'ready',
        activeRepositoryId: repositories[0].id,
    };
};

export const FETCH_DATA_FAILURE = 'FETCH_DATA_FAILURE';
export const fetchRankingFailure = (error) => {
    return {
        type: FETCH_DATA_FAILURE,
        appStatus: 'fail',
        error,
    };
};

export const fetchRanking = (url) => (dispatch) => {
    dispatch(fetchRankingRequest());

    axios
        .get(url)
        .then((response) => {
            return new Promise((resolve, reject) => resolve(response));
        })
        .then((response) => {
            dispatch(fetchRankingSuccess(response));
        })
        .catch((error) => {
            dispatch(fetchRankingFailure(error));
        });
};

export const SHOW_ACTIVE_REPO = 'SHOW_ACTIVE_REPO';
export const showActiveRepository = (index) => {
    return {
        type: SHOW_ACTIVE_REPO,
        activeRepositoryId: index,
    };
};
