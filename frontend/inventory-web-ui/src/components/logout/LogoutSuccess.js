import React, { Component } from 'react';


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
