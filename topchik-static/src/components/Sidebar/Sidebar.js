import React, { useEffect } from 'react';
import './styles.less';
import RepositoryList from '../RepositoryList/RepositoryList';
import AddRepositoryButton from '../AddRepositoryButton/AddRepositoryButton';
import { useSelector, useDispatch } from 'react-redux';
import {
    fetchCategoryTopForPeriod,
    showActiveRepository,
} from './../../redux/ranking/rankingActions';
import { useParams } from 'react-router-dom';

function Sidebar() {
    const { repositories, categories, leaderboards } = useSelector((state) => ({
        repositories: state.repositories,
        categories: state.categories,
        leaderboards: state.leaderboards,
    }));

    const params = useParams();
    const repositoryId = params.repositoryId;

    const dispatch = useDispatch();
    const getWeeklyTops = (url, id, categories) =>
        dispatch(fetchCategoryTopForPeriod(url, id, categories, 'week'));

    const changeActiveRepository = (id) => dispatch(showActiveRepository(id));

    useEffect(() => {
        const selectRepositoryHandler = async (id, categories) => {
            if (
                leaderboards.filter((leaderboard) => leaderboard.repositoryId === id).length === 0
            ) {
                const url = id === 'global' ? '/globalTops' : '/concreteTops';
                await getWeeklyTops(url, id, categories);
            } else {
                await changeActiveRepository(id);
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
