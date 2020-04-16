import React from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import './styles.less';

export default function Repository({ isActive, name, onClickHandler }) {
    const className = classNames('repository', {
        repository_active: isActive,
    });

    return (
        <div className="repository-wrapper">
            <a href="#" className={className} onClick={onClickHandler}>
                {name}
            </a>
        </div>
    );
}

Repository.propTypes = {
    isActive: PropTypes.bool,
    name: PropTypes.string,
    onClickHandler: PropTypes.func,
};
