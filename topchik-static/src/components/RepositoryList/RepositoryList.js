import React from 'react';
import PropTypes from 'prop-types';
import './styles.less';
import Repository from '../Repository/Repository';

export default function RepositoryList({ repositories, activeRepositoryId }) {
    return (
        <div className="repository-list">
            {repositories.map((repository) => {
                return (
                    <Repository
                        title={repository.title}
                        key={repository.id}
                        id={repository.id}
                        isActive={repository.id == activeRepositoryId}
                    />
                );
            })}
        </div>
    );
}

RepositoryList.propTypes = {
    repositories: PropTypes.array,
    activeRepositoryId: PropTypes.number,
};
