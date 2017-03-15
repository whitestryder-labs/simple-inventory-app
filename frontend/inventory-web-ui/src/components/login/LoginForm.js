import React, { Component } from 'react';
import RaisedButton from 'material-ui/RaisedButton';
import TextField from 'material-ui/TextField';
import Card from 'material-ui/Card';
import { hashHistory } from 'react-router'
import './LoginForm.css';


class LoginForm extends Component {

  constructor(props) {
    super(props);

    this.state = {
        errors: {
          summary: "",
        },
        user : {}
    };
  }


  onSubmit = (evt) =>
  {

    const formData = Array.from(evt.target.elements)
            .filter(el => el.name)
            .reduce((a, b) => ({...a, [b.name]: b.value}), {});
    //console.log("formData: " + formData);
    evt.preventDefault();
    
    console.log("Attempting to login with username=" + formData.username);

    var requestBody = {
      "username" : formData.username,
      "password" : formData.password
    };

    fetch('/auth/token', {
        method: 'POST',  
        headers: {
        "Accept" : "application/json",
        "Content-Type" : "application/json",
        "X-Forwarded-Host" : window.location.hostname + ":" + window.location.port
      },
      body: JSON.stringify(requestBody)
    })
    .then(     
        response => {
            
            if (response.status !== 200) {  
                console.log('Looks like there was a problem. Status Code: ' +  
                response.status);
                sessionStorage.removeItem("authToken");
                sessionStorage.removeItem("username");
                this.setState({
                    errors : {
                      summary: "Login failed"
                    }
                }); 
                return;  
            }

            // Examine the text in the response
            var authToken = response.headers.get('x-auth-token');
            response.json().then(
                data =>  {  
                console.log(data);
                if (authToken && data.authenticated && data.username){
                  sessionStorage.setItem("authToken", authToken);
                  sessionStorage.setItem("username", data.username);
                  hashHistory.push('/inventory-listing');
                } else {
                  sessionStorage.removeItem("authToken");
                  sessionStorage.removeItem("username");
                  this.setState({
                    errors : {
                      summary: "Login failed"
                    }
                  });
                  console.log('Login failed.');
                }
        });

    })
    .catch(function(err) {
        this.setState({
                      errors : {
                        summary: err
                      }
                    });
        console.log('Fetch Error :-S', err);  
    });
  }

  onChange(){
    
  }



  render() {
    return (
      <Card className="container">
        <form action="" onSubmit={this.onSubmit}>
          <h2 className="card-heading">Login</h2>

          {this.state.errors.summary !== "" && <p className="error-message">{this.state.errors.summary}</p>}

          <div className="field-line">
            <TextField
              floatingLabelText="Username"
              name="username"
              errorText={this.state.errors.username}
              onChange={this.onChange}
              value={this.state.user.username}
            />
          </div>

          <div className="field-line">
            <TextField
              floatingLabelText="Password"
              type="password"
              name="password"
              onChange={this.onChange}
              errorText={this.state.errors.password}
              value={this.state.user.password}
            />
          </div>

          <div className="button-line">
            <RaisedButton type="submit" label="Log in" primary />
          </div>
        </form>
      </Card>
    );
  }
}


export default LoginForm;
