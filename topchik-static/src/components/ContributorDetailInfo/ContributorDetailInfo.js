import React from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import './styles.less';

export default function ContributorDetailInfo({
    account,
    avatar,
    goldCount,
    silverCount,
    bronzeCount,
}) {
    const medalsCount = [goldCount, silverCount, bronzeCount];

    return (
        <div className="contributor-detail-info">
            <div className="contributor-detail-info__avatar-wrapper">
                <img src={avatar} className="contributor-detail-info__avatar" />
            </div>
            <div className="contributor-detail-info__account-wrapper">
                <div className="contributor-detail-info__account">{account}</div>
                <div className="contributor-detail-info__medals">
                    {medalsCount.map((count, index) => (
                        <div className="contributor-detail-info__medal" key={index}>
                            <div className="contributor-detail-info__medal-icon">
                                <i
                                    className={classNames('fas fa-medal', {
                                        gold: index === 0,
                                        silver: index === 1,
                                        bronze: index === 2,
                                    })}
                                ></i>
                            </div>
                            <div className="contributor-detail-info__medal-count">{count}</div>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}

ContributorDetailInfo.propTypes = {
    account: PropTypes.string,
    avatar: PropTypes.string,
    goldCount: PropTypes.number,
    silverCount: PropTypes.number,
    bronzeCount: PropTypes.number,
};
