import React, { Component } from 'react';
import 'toastr/build/toastr.min.css'
import toastr from 'toastr/toastr';


class Notifier extends Component {

  constructor(props) {
    super(props);

  }

  static error(message){
      toastr.error(message);
  }

  static info(message){
      toastr.info(message);
  }

  static warning(message){
      toastr.warning(message);
  }


    render() {
    return false;
  }

}


export default Notifier;
