import React from 'react';
import './styles.less';
import Header from './components/Header/Header';
import SidebarContainer from './components/SidebarContainer/SidebarContainer';
import MainContentContainer from './components/MainContentContainer/MainContentContainer';

export default function App() {
  return (
    <div className="container">
      <Header />
      <SidebarContainer />
      <MainContentContainer />
    </div>
  );
}
