import React from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import './styles.less';
import { Link } from 'react-router-dom';

export default function Repository({ id, isActive, title }) {
    return (
        <div className="repository">
            <Link
                to={`/repositories/${id}`}
                className={classNames('repository__title', {
                    repository__title_active: isActive,
                })}
            >
                <span>{title}</span>
            </Link>
            <div className="repository__dot-wrapper">
                <div
                    className={classNames('repository__dot', {
                        repository__dot_active: isActive,
                    })}
                ></div>
            </div>
        </div>
    );
}

Repository.propTypes = {
    isActive: PropTypes.bool,
    name: PropTypes.string,
};
