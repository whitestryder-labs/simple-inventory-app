import React, { Component } from 'react';
import Notifier from '../notify/Notifier';


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
      var msg = "Logout Successful.";
      this.setState({
            message: msg
      });
      Notifier.info(msg);
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
