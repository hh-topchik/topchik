import React, { useState } from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import './styles.less';
import PeriodButtons from '../PeriodButtons/PeriodButtons';
import Developer from './../Developer/Developer';

export default function Category({
    id,
    description,
    topWeek,
    topQuarter,
    topYear,
    topAllTime,
    isActive,
}) {
    const tops = {
        week: topWeek,
        quarter: topQuarter,
        year: topYear,
        allTime: topAllTime,
    };
    const [activeTimePeriod, setActiveTimePeriod] = useState('week');
    const developers = tops[activeTimePeriod];

    return (
        <div
            className={classNames('category', {
                category_active: isActive,
            })}
        >
            <h4 className="category__description">{description}</h4>
            <div className="category__period-buttons-wrapper">
                <PeriodButtons
                    onClickHandler={setActiveTimePeriod}
                    activeTimePeriod={activeTimePeriod}
                />
            </div>
            <div className="category__ranking">
                {developers.map((developer, index) => {
                    return (
                        <Developer
                            key={index}
                            account={developer.account}
                            position={index + 1}
                            countPercent={(developer.points / developers[0].points) * 100}
                            count={developer.points}
                        />
                    );
                })}
            </div>
        </div>
    );
}

Category.propTypes = {
    name: PropTypes.string,
    description: PropTypes.string,
    topWeek: PropTypes.array,
    topQuarter: PropTypes.array,
    topYear: PropTypes.array,
    topAllTime: PropTypes.array,
};
