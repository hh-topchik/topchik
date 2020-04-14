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
    const repositories = response.data.repositories.map((item, index) => {
        return {
            id: uuid(),
            name: item.title,
            isActive: false,
            categories: item.sections,
        };
    });

    return {
        type: FETCH_DATA_SUCCESS,
        repositories: repositories,
        appStatus: 'ready',
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
            return new Promise((resolve, reject) => {
                setTimeout(() => resolve(response), 1500);
            });
        })
        .then((response) => {
            dispatch(fetchRankingSuccess(response));
        })
        .catch((error) => {
            dispatch(fetchRankingFailure(error));
        });
};
