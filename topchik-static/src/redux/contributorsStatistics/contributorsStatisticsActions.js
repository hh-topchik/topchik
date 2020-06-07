import axios from 'axios';
const HOST = 'http://' + window.location.hostname + ':8080/api';

export const FETCH_CONTRIBUTORS_SUCCESS = 'FETCH_CONTRIBUTORS_SUCCESS';
export const fetchContributorsSuccess = (response, repositoryId) => {
    const contributors = {
        repositoryId: repositoryId,
        contributors: response.data.contributors,
    };
    return {
        type: FETCH_CONTRIBUTORS_SUCCESS,
        contributors: contributors,
    };
};

export const FETCH_CONTRIBUTORS_FAILURE = 'FETCH_CONTRIBUTORS_FAILURE';
export const fetchContributorsFailure = (error) => {
    return {
        type: FETCH_CONTRIBUTORS_FAILURE,
        error: error,
    };
};

export const FETCH_CONTRIBUTOR_STATISTICS_SUCCESS = 'FETCH_CONTRIBUTOR_STATISTICS_SUCCESS';
export const fetchContributorStatisticsSuccess = (response, repositoryId) => {
    const contributorStatistics = {
        repositoryId: repositoryId,
        contributor: response.data.contributor,
        medals: response.data.medals,
        categoriesStatistics: response.data.categoriesStatistics,
    };
    return {
        type: FETCH_CONTRIBUTOR_STATISTICS_SUCCESS,
        contributorStatistics: contributorStatistics,
    };
};

export const FETCH_CONTRIBUTOR_STATISTICS_FAILURE = 'FETCH_CONTRIBUTOR_STATISTICS_FAILURE';
export const fetchContributorStatisticsFailure = (error) => {
    return {
        type: FETCH_CONTRIBUTOR_STATISTICS_FAILURE,
        error: error,
    };
};

export const fetchContributorsByRepositoryId = (repositoryId) => (dispatch) => {
    axios
        .get(HOST + '/contributors', {
            params: {
                repoId: repositoryId,
            },
        })
        .then((response) => {
            dispatch(fetchContributorsSuccess(response, repositoryId));
        })
        .catch((error) => {
            dispatch(fetchContributorsFailure(error));
        });
};

export const fetchContributorStatisticsByRepositoryId = (repositoryId, accountId) => (dispatch) => {
    axios
        .get(HOST + '/contributorStatistics', {
            params: {
                repoId: repositoryId,
                accountId: accountId,
            },
        })
        .then((response) => {
            dispatch(fetchContributorStatisticsSuccess(response, repositoryId));
        })
        .catch((error) => {
            dispatch(fetchContributorStatisticsFailure(error));
        });
};
