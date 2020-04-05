import React from 'react';
import logoRed from './logo-red.svg';
import './styles.less';

export default function Logo(props) {
    return (
        <a href="https://hh.ru/">
            <img className="logo" src={logoRed} alt="hh.ru" />
        </a>
    );
}
