import React from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import './styles.less';

export default function Developer({
    position,
    countPercent,
    account,
    count,
    showPaintingAnimation,
    onAnimationEndHandler,
}) {
    const classNameIcon = classNames('fas fa-trophy', {
        'developer__icon_first-place': position === 1,
        'developer__icon_second-place': position === 2,
        'developer__icon_third-place': position === 3,
    });

    return (
        <div className="developer">
            <div className="developer__icon">
                {position < 4 ? (
                    <i className={classNameIcon}></i>
                ) : (
                    <div className="developer__position-number">{position}</div>
                )}
            </div>
            <div className="developer__avatar-wrapper">
                <i className="fas fa-user-astronaut developer__avatar"></i>
            </div>
            <div className="developer__count-indicator">
                <div className="developer__name">{account}</div>
                <div className="developer__paint-count-wrapper">
                    <div
                        className="developer__paint-count-container"
                        style={{ width: `${countPercent}%` }}
                    >
                        <div
                            className="developer__paint-count"
                            showpaintinganimation={showPaintingAnimation.toString()}
                            onAnimationEnd={onAnimationEndHandler}
                        ></div>
                    </div>
                </div>
                <div className="developer__additional-info"></div>
            </div>
            <div className="developer__count">{count}</div>
        </div>
    );
}

Developer.propTypes = {
    position: PropTypes.number,
    countPercent: PropTypes.number,
    account: PropTypes.string,
    count: PropTypes.number,
};
