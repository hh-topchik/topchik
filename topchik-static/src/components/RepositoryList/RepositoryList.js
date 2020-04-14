import React from 'react';
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
                        onClick={() => onClickHandler(repository.id)}
                    />
                );
            })}
        </div>
    );
}
