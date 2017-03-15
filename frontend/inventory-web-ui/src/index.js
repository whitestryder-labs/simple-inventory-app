import React from 'react'
import ReactDOM from 'react-dom'
import { Router, Route, hashHistory } from 'react-router'
import App from './components/app/App'
import './index.css'
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider'
import injectTapEventPlugin from 'react-tap-event-plugin'
import InventoryListing from './components/inventory-listing/InventoryListing'
import LoginForm from './components/login/LoginForm'
import LogoutSuccess from './components/logout/LogoutSuccess'

// Needed for onTouchTap
// http://stackoverflow.com/a/34015469/988941
injectTapEventPlugin();

/* NOTE: this file is expected to be named index.js, not index.jsx */

const Main = () => (
  <MuiThemeProvider>
      <Router history={hashHistory}>
      <Route path="/" component={App} >
        <Route path="/inventory-listing" component={InventoryListing}/>
        <Route path="/login" component={LoginForm}/>
        <Route path="/logout" component={LogoutSuccess}/>
      </Route>
    </Router>
  </MuiThemeProvider>
);

ReactDOM.render(
  <Main />,
  document.getElementById('root')
);
