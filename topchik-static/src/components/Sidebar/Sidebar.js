import React, { useCallback } from 'react';
import './styles.less';
import RepositoryList from '../RepositoryList/RepositoryList';
import AddRepositoryButton from '../AddRepositoryButton/AddRepositoryButton';
import { useSelector, useDispatch } from 'react-redux';
import { showActiveRepository } from './../../redux/ranking/rankingActions';

function Sidebar() {
    const { repositories, activeRepositoryId } = useSelector((state) => ({
        repositories: state.repositories,
        activeRepositoryId: state.activeRepositoryId,
    }));

    const dispatch = useDispatch();
    const showActiveRepositoryHandler = useCallback((id) => dispatch(showActiveRepository(id)), [
        dispatch,
    ]);

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
                <RepositoryList
                    repositories={repositories}
                    onClickHandler={showActiveRepositoryHandler}
                    activeRepositoryId={activeRepositoryId}
                />
            </div>
        </aside>
    );
}

export default Sidebar;
