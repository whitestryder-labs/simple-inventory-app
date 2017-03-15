import React, { Component } from 'react';
import RaisedButton from 'material-ui/RaisedButton';
import TextField from 'material-ui/TextField';
import Card from 'material-ui/Card';
import { hashHistory } from 'react-router'


class LogoutSuccess extends Component {

  constructor(props) {
    super(props);

    this.state = {
        message: ""
    };
  }

  componentWillMount() {
      sessionStorage.removeItem('authToken');
      sessionStorage.removeItem('username');
      this.setState({
            message: "Logout successful."
      });
  }

  render() {
    return (
      <div>
        <p>{this.state.message}</p>
        <a href="#/inventory-listing">Back to Inventory Listing</a>
      </div>
    );
  }
}


export default LogoutSuccess;
