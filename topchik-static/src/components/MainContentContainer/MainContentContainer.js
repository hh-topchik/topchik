import React, { Fragment } from 'react';
import Leaderboards from './../Leaderboards/Leaderboards';

export default function MainContentContainer(props) {
  return (
    <main className="content">
      <h1>Repository name</h1>
      <Leaderboards />
    </main>
  );
}
