import React from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import './styles.less';

export default function Repository({ isActive, name, onClickHandler }) {
    return (
        <div className="repository">
            <a
                href="#"
                className={classNames('repository__title', {
                    repository__title_active: isActive,
                })}
                onClick={onClickHandler}
            >
                {name}
            </a>
            <div className="repository__dot-wrapper">
                <div
                    className={classNames('repository__dot', {
                        dot_active: isActive,
                    })}
                ></div>
            </div>
        </div>
    );
}

Repository.propTypes = {
    isActive: PropTypes.bool,
    name: PropTypes.string,
    onClickHandler: PropTypes.func,
};
