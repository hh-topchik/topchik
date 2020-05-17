import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import './styles.less';
import PeriodButtons from '../PeriodButtons/PeriodButtons';
import Developer from './../Developer/Developer';
import { useSelector, useDispatch } from 'react-redux';
import { fetchCategoryTopForPeriod } from '../../redux/ranking/rankingActions';

export default function Category({ refId, categoryId, isActive, refs, description, repositoryId }) {
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

    return (
        <div
            className={classNames('category', {
                category_active: isActive,
            })}
            ref={refs[refId]}
        >
            <h4 className="category__description">{description}</h4>
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
                        const maxDeveloperCount =
                            activeTimePeriod === 'week'
                                ? developers[0].count
                                : developers[0].points;
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
