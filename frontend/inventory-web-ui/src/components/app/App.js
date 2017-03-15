import React, { Component } from 'react';
import './App.css';
import AppBar from 'material-ui/AppBar';
import Drawer from 'material-ui/Drawer';
import MenuItem from 'material-ui/MenuItem';
import { Link } from 'react-router'
import Container from 'muicss/lib/react/container';

class App extends Component {

  constructor(props) {
    super(props);
    this.state = {open: false};
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
            </div>
          </Drawer>
          <Container fluid={true} className="content-container">
            <p>
              Powered by <a href="https://github.com/whitestryder-labs/simple-inventory-app/tree/master/backend/inventory-web-api" >inventory-web-api.</a>
            </p>
            {this.props.children}
          </Container>
      </div>
    );
  }
}



export default App;
