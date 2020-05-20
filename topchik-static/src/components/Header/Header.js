import React from 'react';
import './styles.less';
import Logo from '../Logo/Logo';
import { Link } from 'react-router-dom';

export default function Header() {
    return (
        <header className="header">
            <div className="header__logo">
                <Logo />
            </div>
            <div className="header__title">
                <h1>топчик</h1>
            </div>
            <div className="header__about">
                <Link to={`/about`} className="header__about-text">
                    О проекте
                </Link>
                <Link to={`/about`} className="header__about-icon">
                    <i className="fas fa-info-circle"></i>
                </Link>
            </div>
        </header>
    );
}
