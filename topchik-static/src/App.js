import React, { Fragment, useEffect } from 'react';
import Header from './components/Header/Header';
import Leaderboards from './components/Leaderboards/Leaderboards';
import Sidebar from './components/Sidebar/Sidebar';
import Loader from './components/Loader/Loader';
import Fail from './components/Fail/Fail';
import './styles.less';
import { useSelector, useDispatch } from 'react-redux';
import { fetchRepositoriesAndCategories } from './redux/ranking/rankingActions';
import { Redirect, Route } from 'react-router-dom';

function App() {
    const { appStatus } = useSelector((state) => ({
        appStatus: state.appStatus,
    }));

    const dispatch = useDispatch();
    const getRepositoriesAndCategories = () => dispatch(fetchRepositoriesAndCategories());

    useEffect(() => {
        getRepositoriesAndCategories();
    }, []);

    return (
        <div className="app">
            <div className="app__header">
                <Header />
            </div>
            <div className="content">
                {appStatus === 'not ready' ? (
                    <Loader />
                ) : appStatus === 'fail' ? (
                    <Fail />
                ) : (
                    <Fragment>
                        <Redirect from="/" to="/repositories/global" />
                        <Route path="/repositories/:repositoryId" component={Sidebar} />
                        <Route path="/repositories/:repositoryId" component={Leaderboards} />
                    </Fragment>
                )}
            </div>
        </div>
    );
}

export default App;
