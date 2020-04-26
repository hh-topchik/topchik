import React from 'react';
import './styles.less';
import Logo from '../Logo/Logo';

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
                <a className="header__about-text" href="#">
                    О проекте
                </a>
                <div className="header__about-icon">
                    <i className="fas fa-info-circle"></i>
                </div>
            </div>
        </header>
    );
}
