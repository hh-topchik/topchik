import React from 'react';
import PropTypes from 'prop-types';
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
                        onClick={onClickHandler}
                    >
                        {button.periodView}
                    </button>
                );
            })}
        </div>
    );
}

PeriodButtons.propTypes = {
    onClickHandler: PropTypes.func,
    activeTimePeriod: PropTypes.oneOf(['week', 'quarter', 'year', 'allTime']),
};
