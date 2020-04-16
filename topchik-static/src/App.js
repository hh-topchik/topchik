import React, { Fragment, useEffect, useCallback } from 'react';
import Header from './components/Header/Header';
import Leaderboards from './components/Leaderboards/Leaderboards';
import Sidebar from './components/Sidebar/Sidebar';
import Loader from './components/Loader/Loader';
import './styles.less';
import { useSelector, useDispatch } from 'react-redux';
import { fetchRanking } from './redux/ranking/rankingActions';

function App() {
    const { appStatus, activeRepositoryId } = useSelector((state) => ({
        repositories: state.repositories,
        appStatus: state.appStatus,
        activeRepositoryId: state.activeRepositoryId,
    }));

    const dispatch = useDispatch();
    const getRanking = useCallback((url) => dispatch(fetchRanking(url)), [dispatch]);

    useEffect(() => {
        getRanking('http://localhost:9200/api/getRanking');
    }, []);

    return (
        <div className="app">
            <Header />

            <div className="content">
                {appStatus === 'not ready' ? (
                    <Loader />
                ) : (
                    <Fragment>
                        <Sidebar />
                        {activeRepositoryId !== '0' ? <Leaderboards /> : null}
                    </Fragment>
                )}
            </div>
        </div>
    );
}

export default App;
