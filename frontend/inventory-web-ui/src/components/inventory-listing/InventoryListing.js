import React, { Component } from 'react';
import IconButton from 'material-ui/IconButton';
import FlatButton from 'material-ui/FlatButton';
import ActionRefresh from 'material-ui/svg-icons/navigation/refresh';
import {List, ListItem} from 'material-ui/List';
import {Card, CardTitle, CardText, CardActions} from 'material-ui/Card';
import Container from 'muicss/lib/react/container';
// import 'toastr/build/toastr.min.css'
// import toastr from 'toastr/toastr';
import Notifier from '../notify/Notifier';
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
                description: item.description,
                price: item.price,
                quantityInStock: item.quantityInStock
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
                var errMsg = 'Unable to retrieve inventory items. Status Code: ' + response.status;
                console.log(errMsg);
                Notifier.error(errMsg);
                return;  
            }


            // Examine the text in the response  
            response.json().then(
                data => {  
                console.log(data);
                this.setState({
                    itemsResult: this.itemResultsFromInventoryItems(data)
                });
                Notifier.info("Retrieved " + this.state.itemsResult.count + " items ", "success", 5000, "green");
        });
    })
    .catch(function(err) {
        console.log('Fetch Error :-S', err);
        Notifier.error("Unable to retrieve inventory items, reason: " + err, "error", 5000, "red");
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
                                <div>{row.description}</div>
                                <div className="horiz">
                                    <div  className="horiz">
                                        <label className="item-label price">Price:</label><span>${row.price}</span>
                                    </div>
                                    <div className="horiz">
                                        <label className="item-label quantity">Quantity In-stock:</label><span>{row.quantityInStock}</span>
                                    </div>
                                </div>
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