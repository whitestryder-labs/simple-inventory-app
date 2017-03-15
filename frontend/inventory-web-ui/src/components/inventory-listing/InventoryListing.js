import React, { Component } from 'react';
import IconButton from 'material-ui/IconButton';
import FlatButton from 'material-ui/FlatButton';
import ActionRefresh from 'material-ui/svg-icons/navigation/refresh';
import {List, ListItem} from 'material-ui/List';
import {Card, CardTitle, CardText, CardActions} from 'material-ui/Card';
import Container from 'muicss/lib/react/container';
import './InventoryListing.css';


class InventoryListing extends Component {



  constructor(props) {
    super(props);

    this.state = {
      refreshing: false,
      itemsResult: {
          took: 0,
          hits: 0,
          list: []
      }
    };
  }


componentWillMount() {
    this.refreshItems();
}




 resetSearchResult(){
     this.setState({
      itemsResult: {
          list: []
      }
    });
 }

 itemResultsFromInventoryItems(data){
     var result = {};
     result.count = data.items.length;
     result.list = [];
     data.items.forEach(function(item) {
        result.list.push(
            {
                name: item.name,
                description: item.description
            }
        )
     });
     return result;
 }

  refreshItems = () =>
  {
    this.resetSearchResult();
    console.log("Refreshing inventory listing");

    fetch('/api/inventory-item', {
        method: 'GET',  
        headers: {
        "Accept" : "application/json"
        }
    })
    .then(  
        response => {
            if (response.status !== 200) {  
                console.log('Looks like there was a problem. Status Code: ' +  
                response.status);  
                return;  
            }


            // Examine the text in the response  
            response.json().then(
                data => {  
                console.log(data);
                this.setState({
                    itemsResult: this.itemResultsFromInventoryItems(data)
                });
        });
    })
    .catch(function(err) {  
        console.log('Fetch Error :-S', err);  
    });
  }
  


  render() {
    return (
        <Container fluid={true}>
            
            <h1>Inventory Listing</h1>

            <div className="horiz">
                <div className="horiz">
                    <IconButton tooltip="Refresh items" onClick={this.refreshItems}>
                        <ActionRefresh />
                    </IconButton>
                </div>
                <h3 className="horiz" >Items found: {this.state.itemsResult.count}</h3>

            </div>

            <List>
                {this.state.itemsResult.list.map( (row, index) => (
                    <ListItem key={index}>
                        <Card>
                            <CardTitle title={row.name} />
                            <CardText>
                                {row.description}
                            </CardText>
                            <CardActions>
                            <FlatButton label="View Details" />
                            <FlatButton label="Buy this item" />
                            </CardActions>
                        </Card>
                    </ListItem>
                ))}
            </List>
        </Container>
    );
  }
}

export default InventoryListing;