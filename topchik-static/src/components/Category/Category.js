import React, { useState } from 'react';
import './styles.less';
import PeriodButtons from '../PeriodButtons/PeriodButtons';
import Developer from './../Developer/Developer';

export default function Category({ name, description, topWeek, topQuarter, topYear, topAllTime }) {
    const tops = {
        week: topWeek,
        quarter: topQuarter,
        year: topYear,
        allTime: topAllTime,
    };
    const [activeTimePeriod, setActiveTimePeriod] = useState('week');
    const developers = tops[activeTimePeriod];

    return (
        <div className="category">
            <h3 className="category__name">{name}</h3>
            <h4 className="category__description">{description}</h4>
            <PeriodButtons
                onClickHandler={setActiveTimePeriod}
                activeTimePeriod={activeTimePeriod}
            />
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
