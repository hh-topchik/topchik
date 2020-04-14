import React from 'react';
import classNames from 'classnames';
import './styles.less';

const buttons = [
    { periodValue: 'week', periodView: 'за неделю' },
    { periodValue: 'quarter', periodView: 'за квартал' },
    { periodValue: 'year', periodView: 'за год' },
    { periodValue: 'allTime', periodView: 'за всё время' },
];

export default function PeriodButtons({ onClickHandler, activeTimePeriod }) {
    return (
        <div className="period-buttons">
            {buttons.map((button, index) => {
                const classNameButton = classNames('period-buttons__button', {
                    'period-buttons__button_active': activeTimePeriod === button.periodValue,
                });
                return (
                    <button
                        key={index}
                        className={classNameButton}
                        value={button.periodValue}
                        onClick={(e) => onClickHandler(e.target.value)}
                    >
                        {button.periodView}
                    </button>
                );
            })}
        </div>
    );
}
