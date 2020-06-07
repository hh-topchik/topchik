import React, { useEffect, useState } from 'react';
import './styles.less';
import RepositoryList from '../RepositoryList/RepositoryList';
import AddRepositoryButton from '../AddRepositoryButton/AddRepositoryButton';
import { useSelector, useDispatch } from 'react-redux';
import {
    fetchCategoryTopForPeriod,
    showActiveRepository,
} from './../../redux/ranking/rankingActions';
import { fetchContributorsByRepositoryId } from '../../redux/ranking/rankingActions';
import { useParams } from 'react-router-dom';

function Sidebar() {
    const { repositories, categories, leaderboards, contributors } = useSelector((state) => ({
        repositories: state.repositories,
        categories: state.categories,
        leaderboards: state.leaderboards,
        contributors: state.contributors,
    }));

    const params = useParams();
    const repositoryId = params.repositoryId;

    const dispatch = useDispatch();

    const getWeeklyTops = (url, id, categories) =>
        dispatch(fetchCategoryTopForPeriod(url, id, categories, 'week'));

    const getContributorsByRepoId = (repositoryId) =>
        dispatch(fetchContributorsByRepositoryId(repositoryId));

    const changeActiveRepository = (id) => dispatch(showActiveRepository(id));

    useEffect(() => {
        const currentRepositoryContributors = contributors.filter(
            (repositoryContributors) => repositoryContributors.repositoryId === repositoryId,
        );

        const needRequestContributors =
            contributors.length === 0 || currentRepositoryContributors.length === 0;

        const getContributors = (repositoryId) => {
            if (needRequestContributors) {
                getContributorsByRepoId(repositoryId);
            }
        };
        const selectRepositoryHandler = (id, categories) => {
            if (
                leaderboards.filter((leaderboard) => leaderboard.repositoryId === id).length === 0
            ) {
                const url = id === 'global' ? '/globalTops' : '/concreteTops';

                getWeeklyTops(url, id, categories);
                getContributors(id);
            } else {
                changeActiveRepository(id);
            }
        };
        selectRepositoryHandler(repositoryId, categories);
    }, [repositoryId]);

    return (
        <aside className="content__sidebar sidebar">
            <div className="sidebar__heading-wrapper">
                <h3 className="sidebar__heading">Проекты</h3>
                <div className="sidebar__add-repository-button">
                    <AddRepositoryButton
                        onClickHandler={() => console.log('add repository click')}
                    />
                </div>
            </div>
            <div className="sidebar__list-wrapper">
                <RepositoryList repositories={repositories} activeRepositoryId={repositoryId} />
            </div>
        </aside>
    );
}

export default Sidebar;
