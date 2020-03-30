import React from 'react';
import './styles.less';
import Logo from '../logo/Logo';

export default function Header() {
  return (
    <header className="header">
      <div className="header__logo">
        <Logo />
      </div>
      <div className="header_title">
        <h1>topchik</h1>
      </div>
    </header>
  );
}
