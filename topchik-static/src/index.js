import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { createStore, applyMiddleware } from 'redux';
import rootReducer from './redux/index';
import thunkMiddleware from 'redux-thunk';
import App from './App';
import './common/css/normalize.less';
import { BrowserRouter } from 'react-router-dom';
import { createBrowserHistory } from 'history';

const store = createStore(rootReducer, applyMiddleware(thunkMiddleware));
store.subscribe(() => console.log('store', store.getState()));

const history = createBrowserHistory();

ReactDOM.render(
    <Provider store={store}>
        <BrowserRouter>
            <App />
        </BrowserRouter>
    </Provider>,
    document.getElementById('root'),
);
