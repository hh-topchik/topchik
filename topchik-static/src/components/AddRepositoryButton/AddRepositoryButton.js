import React from 'react';
import PropTypes from 'prop-types';
import './styles.less';

export default function AddRepositoryButton({ onClickHandler }) {
    return (
        <button className="add-repository-button" onClick={onClickHandler}>
            <i className="fas fa-plus"></i>
        </button>
    );
}

AddRepositoryButton.propTypes = {
    onClickHandler: PropTypes.func.isRequired,
};
