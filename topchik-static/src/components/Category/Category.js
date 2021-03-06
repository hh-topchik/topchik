import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import './styles.less';
import PeriodButtons from '../PeriodButtons/PeriodButtons';
import Developer from './../Developer/Developer';
import { useSelector, useDispatch } from 'react-redux';
import { fetchCategoryTopForPeriod } from '../../redux/ranking/rankingActions';

export default function Category({
    refId,
    categoryId,
    isActive,
    refs,
    description,
    repositoryId,
    unitOfMeasure,
    title,
}) {
    const { leaderboards } = useSelector((state) => ({
        leaderboards: state.leaderboards,
    }));

    const [activeTimePeriod, setActiveTimePeriod] = useState('week');
    const [showPaintingAnimation, setShowPaintingAnimation] = useState(true);

    useEffect(() => {
        setShowPaintingAnimation(true);
    }, [repositoryId, activeTimePeriod]);

    useEffect(() => {
        setActiveTimePeriod('week');
    }, [repositoryId]);

    const developers = leaderboards
        .filter(
            (leaderboard) =>
                leaderboard.repositoryId === repositoryId &&
                leaderboard.categoryId === categoryId &&
                leaderboard.period === activeTimePeriod,
        )
        .map((leaderboard) => leaderboard.top)[0];

    const dispatch = useDispatch();
    const getTopForPeriod = (url, repositoryId, categoryIds, period) =>
        dispatch(fetchCategoryTopForPeriod(url, repositoryId, categoryIds, period));

    const onClickPeriod = async (event, activeRepositoryId, categoryId) => {
        const newPeriod = event.target.value;
        const url = activeRepositoryId === 'global' ? '/globalTops' : '/concreteTops';

        if (
            leaderboards.filter(
                (leaderboard) =>
                    leaderboard.repositoryId === activeRepositoryId &&
                    leaderboard.categoryId === categoryId &&
                    leaderboard.period === newPeriod,
            ).length === 0
        ) {
            await getTopForPeriod(url, activeRepositoryId, [{ id: categoryId }], newPeriod);
        }

        setActiveTimePeriod(newPeriod);
        setShowPaintingAnimation(true);
    };

    const counts = !!developers
        ? developers.map((developer) =>
              activeTimePeriod === 'week' ? developer.count : developer.points,
          )
        : [];
    const maxDeveloperCount = Math.max(...counts);

    return (
        <div
            className={classNames('category', {
                category_active: isActive,
            })}
            ref={refs[refId]}
        >
            <h3 className="category__title">{title}</h3>
            <div className="category__description">{description}</div>
            <div className="category__period-buttons-wrapper">
                <PeriodButtons
                    activeTimePeriod={activeTimePeriod}
                    onClickHandler={onClickPeriod}
                    activeRepositoryId={repositoryId}
                    categoryId={categoryId}
                />
            </div>
            {!!developers && developers.length > 0 ? (
                <div className="category__ranking">
                    {developers.map((developer, index) => {
                        const developerCount =
                            activeTimePeriod === 'week' ? developer.count : developer.points;
                        return (
                            <Developer
                                key={index}
                                account={developer.account}
                                position={index + 1}
                                countPercent={(developerCount / maxDeveloperCount) * 100}
                                count={developerCount}
                                onAnimationEndHandler={() => setShowPaintingAnimation(false)}
                                showPaintingAnimation={showPaintingAnimation}
                                avatar={developer.avatar}
                                unitOfMeasure={
                                    activeTimePeriod === 'week'
                                        ? unitOfMeasure
                                        : declOfNum(developerCount, ['очко', 'очка', 'очков'])
                                }
                                categoryId={categoryId}
                            />
                        );
                    })}
                </div>
            ) : (
                <div className="category__empty-category-message">
                    В этом периоде никто не совершал никаких действий 🤷‍♂️
                </div>
            )}
        </div>
    );
}

Category.propTypes = {
    name: PropTypes.string,
    title: PropTypes.string,
};

function declOfNum(number, titles) {
    const cases = [2, 0, 1, 1, 1, 2];
    return titles[
        number % 100 > 4 && number % 100 < 20 ? 2 : cases[number % 10 < 5 ? number % 10 : 5]
    ];
}
