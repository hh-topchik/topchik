import React, { useState, useCallback, useEffect, useRef } from 'react';
import './styles.less';
import Category from '../Category/Category';
import CategoryNavigation from '../CategoryNavigation/CategoryNavigation';
import { useSelector } from 'react-redux';

function Leaderboards() {
    const { activeRepositoryId, repositories } = useSelector((state) => ({
        repositories: state.repositories,
        activeRepositoryId: state.activeRepositoryId,
    }));

    const currentRepository = repositories.filter(
        (repository) => repository.id === activeRepositoryId,
    )[0];

    const [activeCategoryId, setActiveCategoryId] = useState(0);

    return (
        <main className="content__leaderboards leaderboards">
            <div className="leaderboards__category-buttons-group">
                <div className="leaderboards__category-heading-wrapper">
                    <h3 className="leaderboards__category-heading">Категории</h3>
                </div>
                <div className="leaderboards__category-buttons">
                    <CategoryNavigation
                        categoryTitles={currentRepository.categories.map(
                            (category) => category.name,
                        )}
                        activeCategoryId={activeCategoryId}
                        onClickHandler={setActiveCategoryId}
                    />
                </div>
            </div>
            <div className="leaderboards__categories">
                {currentRepository.categories.map((category, index) => (
                    <Category
                        key={index}
                        id={index}
                        isActive={activeCategoryId === index}
                        description={category.description}
                        topWeek={category.topWeek}
                        topQuarter={category.topQuarter}
                        topYear={category.topYear}
                        topAllTime={category.topAllTime}
                    />
                ))}
            </div>
        </main>
    );
}

export default Leaderboards;
