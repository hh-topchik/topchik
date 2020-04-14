import React from 'react';
import './styles.less';
import Category from '../Category/Category';
import { useSelector } from 'react-redux';

function Leaderboards() {
    const { activeRepositoryId, repositories } = useSelector((state) => ({
        repositories: state.repositories,
        activeRepositoryId: state.activeRepositoryId,
    }));

    const currentRepository = repositories.filter(
        (repository) => repository.id === activeRepositoryId,
    )[0];

    return (
        <main className="content__leaderboards leaderboards">
            <div className="leaderboards__heading-wrapper">
                <h2 className="leaderboards__heading">{currentRepository.name}</h2>
            </div>
            {currentRepository.categories.map((category, index) => (
                <Category
                    key={index}
                    name={category.name}
                    description={category.description}
                    topWeek={category.topWeek}
                    topQuarter={category.topQuarter}
                    topYear={category.topYear}
                    topAllTime={category.topAllTime}
                />
            ))}
        </main>
    );
}

export default Leaderboards;
