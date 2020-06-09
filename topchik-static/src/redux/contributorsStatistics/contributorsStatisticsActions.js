import axios from 'axios';
const HOST = 'http://' + window.location.hostname + ':8080/api';

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

export const fetchContributorStatisticsByRepositoryId = (repositoryId, accountId) => (dispatch) => {
    const parameters =
        repositoryId === 'global'
            ? {
                  params: {
                      accountId: accountId,
                  },
              }
            : {
                  params: {
                      repoId: repositoryId,
                      accountId: accountId,
                  },
              };
    axios
        .get(HOST + '/contributorStatistics', parameters)
        .then((response) => {
            dispatch(fetchContributorStatisticsSuccess(response, repositoryId));
        })
        .catch((error) => {
            dispatch(fetchContributorStatisticsFailure(error));
        });
};
