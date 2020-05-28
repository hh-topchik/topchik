import React from 'react';
import PropTypes from 'prop-types';
import './styles.less';

export default function ContributorSelect({ contributors, onChange, activeAccount }) {
    return (
        <div className="contributor-select">
            <span className="contributor-select__title">Разработчики:</span>
            <label className="contributor-select__label" htmlFor="contributor-select__select">
                <select
                    className="contributor-select__select"
                    value={activeAccount}
                    onChange={(e) => onChange(e.target.value)}
                >
                    {contributors.map((contributor, index) => (
                        <option key={index} value={contributor.account}>
                            {contributor.account}
                        </option>
                    ))}
                </select>
            </label>
        </div>
    );
}

ContributorSelect.propTypes = {
    contributors: PropTypes.array,
    onChange: PropTypes.func,
    activeAccount: PropTypes.string,
};
