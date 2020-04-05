import React from 'react';
import './styles.less';

export default function Button({ variant, onClickHandler }) {
    switch (variant) {
        case 'add-repository':
            return <AddRepositoryButton onClickHandler={onClickHandler} />;
        default:
            return null;
    }
}

function AddRepositoryButton({ onClickHandler }) {
    return (
        <button
            className="button-default button-default_add-repository-button"
            onClick={onClickHandler}
        >
            <i className="fas fa-plus"></i>
        </button>
    );
}
