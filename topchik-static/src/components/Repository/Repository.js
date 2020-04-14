import React from 'react';
import classNames from 'classnames';
import './styles.less';

export default function Repository({ isActive, name, onClick }) {
    const className = classNames('repository', {
        repository_active: isActive,
    });

    return (
        <div className="repository-wrapper">
            <a href="#" className={className} onClick={onClick}>
                {name}
            </a>
        </div>
    );
}
