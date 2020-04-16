import React from 'react';
import './styles.less';
import Logo from '../Logo/Logo';

export default function Header() {
    return (
        <header className="app__header header">
            <div className="header__logo">
                <Logo />
            </div>
            <div className="header__title">
                <h1>топчик</h1>
            </div>
            <div className="header__about">
                <p>О проекте</p>
            </div>
        </header>
    );
}
