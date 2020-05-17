import React from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import './styles.less';

function CategoryButton({ onClickHandler, categoryTitle, id, activeCategoryId }) {
    return (
        <a
            className={classNames('category-button', {
                'category-button_active': activeCategoryId === id,
            })}
            onClick={() => onClickHandler(id)}
        >
            {categoryTitle}
        </a>
    );
}

CategoryButton.propTypes = {
    onClickHandler: PropTypes.func.isRequired,
    categoryTitle: PropTypes.string,
    activeCategoryId: PropTypes.number,
    id: PropTypes.number,
};

export default function CategoryNavigation({ categories, onClickHandler, activeCategoryId }) {
    return (
        <div className="category-navigation">
            {categories.map((category, index) => (
                <CategoryButton
                    onClickHandler={onClickHandler}
                    activeCategoryId={activeCategoryId}
                    id={index}
                    key={index}
                    categoryTitle={category.title}
                />
            ))}
        </div>
    );
}

CategoryNavigation.propTypes = {
    onClickHandler: PropTypes.func.isRequired,
    categoryTitles: PropTypes.array,
    activeCategoryId: PropTypes.number,
};
