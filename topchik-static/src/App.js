import React, { Fragment, useEffect } from 'react';
import Header from './components/Header/Header';
import Leaderboards from './components/Leaderboards/Leaderboards';
import Sidebar from './components/Sidebar/Sidebar';
import Loader from './components/Loader/Loader';
import Fail from './components/Fail/Fail';
import About from './components/About/About';
import ContributorsStatistics from './components/ContributorsStatistics/ContributorsStatistics';
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
                        <Route exact path="/repositories/:repositoryId" component={Leaderboards} />
                        <Route path="/about" component={About} />
                        <Route
                            exact
                            path="/repositories/:repositoryId/contributorsStatistics"
                            component={ContributorsStatistics}
                        />
                    </Fragment>
                )}
            </div>
        </div>
    );
}

export default App;
