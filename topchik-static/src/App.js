import React, { Fragment, useEffect, useCallback } from 'react';
import Header from './components/Header/Header';
import Leaderboards from './components/Leaderboards/Leaderboards';
import Sidebar from './components/Sidebar/Sidebar';
import Loader from './components/Loader/Loader';
import Fail from './components/Fail/Fail';
import './styles.less';
import { useSelector, useDispatch } from 'react-redux';
import { fetchRanking } from './redux/ranking/rankingActions';

function App() {
    const { appStatus } = useSelector((state) => ({
        appStatus: state.appStatus,
    }));

    const dispatch = useDispatch();
    const getRanking = useCallback((url) => dispatch(fetchRanking(url)), [dispatch]);

    useEffect(() => {
        getRanking('http://localhost:9200/api/getRanking');
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
                        <Sidebar />
                        <Leaderboards />
                    </Fragment>
                )}
            </div>
        </div>
    );
}

export default App;
