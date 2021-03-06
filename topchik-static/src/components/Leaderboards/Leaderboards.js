import React, { useState } from 'react';
import './styles.less';
import Category from '../Category/Category';
import CategoryNavigation from '../CategoryNavigation/CategoryNavigation';
import { useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';

function Leaderboards() {
    const { categories } = useSelector((state) => ({
        categories: state.categories,
    }));

    const [activeCategoryId, setActiveCategoryId] = useState(0);

    const params = useParams();
    const repositoryId = params.repositoryId;

    const categoryRefs = categories.map(() => {
        return React.createRef();
    });

    const onClickCategoryButtonHandler = (id) => {
        categoryRefs[id].current.scrollIntoView({
            behavior: 'smooth',
            block: 'end',
            inline: 'nearest',
        });
        if (window.innerWidth <= 1000) {
            setActiveCategoryId(id);
        }
    };

    const handleScroll = () => {
        const activeCategoryElement = categoryRefs[activeCategoryId].current;
        // все категории изначально смещены на значение offsetTop для первой категории
        // поэтому границу переключения при скролле ВНИЗ нужно задавать как изначальное смещение МИНУС высоты текущей категории
        const lowerLimit = categoryRefs[0].current.offsetTop - activeCategoryElement.clientHeight;
        // и границу переключения при скролле ВВЕРХ нужно задавать как изначальное смещение ПЛЮС высоты текущей категории
        const upperLimit = categoryRefs[0].current.offsetTop + activeCategoryElement.clientHeight;
        const scrollTop = activeCategoryElement.getBoundingClientRect().top;
        // если upperLimit получился больше чем видимая область, то нужно отнять разницу между upperLimit и window.innerHeight от window.innerHeight
        // и использовать это число как верхнее ограничение (для экранов меньше 900 пикселей)
        const adaptiveUpperLimit =
            upperLimit > window.innerHeight ? 2 * window.innerHeight - upperLimit : upperLimit;

        let calculatedActiveCategory = activeCategoryId;
        if (scrollTop < lowerLimit + 60) {
            calculatedActiveCategory++;
        } else if (scrollTop > adaptiveUpperLimit) {
            calculatedActiveCategory--;
        }

        if (calculatedActiveCategory !== activeCategoryId) {
            setActiveCategoryId(calculatedActiveCategory);
        }
    };

    return (
        <main className="content__leaderboards leaderboards" onScroll={handleScroll}>
            <div className="leaderboards__category-buttons-group">
                <div className="leaderboards__category-buttons">
                    <CategoryNavigation
                        categories={categories}
                        activeCategoryId={activeCategoryId}
                        onClickHandler={onClickCategoryButtonHandler}
                    />
                </div>
            </div>
            <div className="leaderboards__categories">
                {categories.map((category, index) => (
                    <Category
                        key={index}
                        categoryId={category.id}
                        refId={index}
                        refs={categoryRefs}
                        isActive={activeCategoryId === index}
                        title={category.title}
                        description={category.description}
                        repositoryId={repositoryId}
                        unitOfMeasure={category.unitOfMeasure}
                        title={category.title}
                    />
                ))}
            </div>
        </main>
    );
}

export default Leaderboards;
