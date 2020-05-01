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

    const categoryRefs = currentRepository.categories.map(() => {
        return React.createRef();
    });

    const onClickCategoryButtonHandler = (id) => {
        categoryRefs[id].current.scrollIntoView({
            behavior: 'smooth',
            block: 'end',
            inline: 'nearest',
        });
    };

    const handleScroll = () => {
        const activeCategoryElement = categoryRefs[activeCategoryId].current;
        // все категории изначально смещены на значение offsetTop для первой категории
        // поэтому границу переключения при скролле ВНИЗ нужно задавать как изначальное смещение МИНУС высоты текущей категории
        const lowerLimit = categoryRefs[0].current.offsetTop - activeCategoryElement.clientHeight;
        // и границу переключения при скролле ВВЕРХ нужно задавать как изначальное смещение ПЛЮС высоты текущей категории
        const upperLimit = categoryRefs[0].current.offsetTop + activeCategoryElement.clientHeight;
        const scrollTop = activeCategoryElement.getBoundingClientRect().top;

        let calculatedActiveCategory = activeCategoryId;
        if (scrollTop < lowerLimit) {
            calculatedActiveCategory++;
        } else if (scrollTop > upperLimit) {
            calculatedActiveCategory--;
        }

        if (calculatedActiveCategory !== activeCategoryId) {
            setActiveCategoryId(calculatedActiveCategory);
        }
    };

    return (
        <main className="content__leaderboards leaderboards" onScroll={handleScroll}>
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
                        onClickHandler={onClickCategoryButtonHandler}
                    />
                </div>
            </div>
            <div className="leaderboards__categories">
                {currentRepository.categories.map((category, index) => (
                    <Category
                        key={index}
                        id={index}
                        refs={categoryRefs}
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
