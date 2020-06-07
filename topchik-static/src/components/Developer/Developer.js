import React from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import './styles.less';
import { Link, useParams } from 'react-router-dom';

export default function Developer({
    position,
    countPercent,
    account,
    count,
    showPaintingAnimation,
    onAnimationEndHandler,
    avatar,
    unitOfMeasure,
    categoryId,
}) {
    const classNameIcon = classNames('fas fa-medal', {
        'developer__icon_first-place': position === 1,
        'developer__icon_second-place': position === 2,
        'developer__icon_third-place': position === 3,
    });

    const params = useParams();
    const repositoryId = params.repositoryId;

    return (
        <div className="developer">
            <div className="developer__icon">
                {position < 4 ? (
                    <i className={classNameIcon}></i>
                ) : (
                    <div className="developer__position-number">{position}</div>
                )}
            </div>
            <Link
                to={{
                    pathname: `/repositories/${repositoryId}/contributorsStatistics`,
                    state: { account: account, categoryId: categoryId },
                }}
                className="developer__avatar-wrapper"
            >
                <img src={avatar} className="developer__avatar" />
            </Link>
            <div className="developer__count-indicator">
                <Link
                    to={{
                        pathname: `/repositories/${repositoryId}/contributorsStatistics`,
                        state: { account: account, categoryId: categoryId },
                    }}
                    className="developer__name"
                >
                    {account}
                </Link>
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
            <div className="developer__count">
                {count} {unitOfMeasure}
            </div>
        </div>
    );
}

Developer.propTypes = {
    position: PropTypes.number,
    countPercent: PropTypes.number,
    account: PropTypes.string,
    count: PropTypes.number,
};
