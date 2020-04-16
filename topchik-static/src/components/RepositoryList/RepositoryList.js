import React from 'react';
import PropTypes from 'prop-types';
import './styles.less';
import RepoItem from '../Repository/Repository';

export default function RepositoryList({ repositories, onClickHandler, activeRepositoryId }) {
    return (
        <div className="repository-list">
            {repositories.map((repository) => {
                return (
                    <RepoItem
                        name={repository.name}
                        key={repository.id}
                        id={repository.id}
                        isActive={repository.id === activeRepositoryId}
                        onClickHandler={() => onClickHandler(repository.id)}
                    />
                );
            })}
        </div>
    );
}

RepositoryList.propTypes = {
    repositories: PropTypes.array,
    activeRepositoryId: PropTypes.string,
    onClickHandler: PropTypes.func,
};
