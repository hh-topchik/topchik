import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { createStore, applyMiddleware } from 'redux';
import rootReducer from './redux/index';
import thunkMiddleware from 'redux-thunk';
import App from './App';

import './common/normalize.less';
import './common/fonts.less';

const store = createStore(rootReducer, applyMiddleware(thunkMiddleware));
store.subscribe(() => console.log('store', store.getState()));

ReactDOM.render(
    <Provider store={store}>
        <App />
    </Provider>,
    document.getElementById('root'),
);
