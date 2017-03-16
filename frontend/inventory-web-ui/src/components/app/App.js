import React, { Component } from 'react';
import './App.css';
import AppBar from 'material-ui/AppBar';
import Drawer from 'material-ui/Drawer';
import MenuItem from 'material-ui/MenuItem';
import { Link } from 'react-router'
import Container from 'muicss/lib/react/container';
import IconButton from 'material-ui/IconButton';
import IconLock from 'material-ui/svg-icons/action/lock';


class App extends Component {

  constructor(props) {
    super(props);

    this.state = {
      open: false,
      username: function() {
        var username = sessionStorage.getItem('username');
        if (username == null){
          return "";
        } else {
          return username;
        }
    },
      authenticated: false,
      hasAuthToken : function() {
            var token = sessionStorage.getItem('authToken');
            return token != null;
        }
    };
  }

  

  handleLeftIconButtonClick = () => this.setState({open: !this.state.open});

  handleLeftMenuClose = () => this.setState({open: false});

  render() {
    return (
      <div>
          <AppBar
              title="Simple Inventory App"
              iconClassNameRight="muidocs-icon-navigation-expand-more"
              onLeftIconButtonTouchTap={this.handleLeftIconButtonClick}
          />
          <Drawer 
            docked={false}
            open={this.state.open}
            onRequestChange={(open) => this.setState({open})}
            >
            <div>
              <AppBar
                title=""
                onLeftIconButtonTouchTap={this.handleLeftIconButtonClick}
              />
              <MenuItem onTouchTap={this.handleLeftMenuClose}><Link to="/inventory-listing">Inventory Listing</Link></MenuItem>
              {
                this.state.hasAuthToken() && 
                <MenuItem onTouchTap={this.handleLeftMenuClose}>
                  <Link to="/logout">Logout ({this.state.username()})</Link>
                </MenuItem>
              }
              {
                !this.state.hasAuthToken() && 
                <MenuItem onTouchTap={this.handleLeftMenuClose}>
                  <Link to="/login">Login</Link>
                </MenuItem>
              }
            </div>
          </Drawer>
          <Container fluid={true} className="content-container">
            <div className="horiz">
              <div className="horiz">
                <p>
                  Powered by <a href="https://github.com/whitestryder-labs/simple-inventory-app/tree/master/backend/inventory-web-api" >inventory-web-api.</a>
                </p>
              </div>
              <div className="horiz">
                {
                  this.state.hasAuthToken() && 
                  <IconButton tooltip="User is Authenticated">
                    <IconLock />
                  </IconButton>
                }
              </div>
            </div>
            {this.props.children}
          </Container>
      </div>
    );
  }
}



export default App;
