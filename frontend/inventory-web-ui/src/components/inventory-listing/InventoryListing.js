import React, { Component } from 'react';
import IconButton from 'material-ui/IconButton';
import FlatButton from 'material-ui/FlatButton';
import RaisedButton from 'material-ui/RaisedButton';
import Dialog from 'material-ui/Dialog';
import ActionRefresh from 'material-ui/svg-icons/navigation/refresh';
import {List, ListItem} from 'material-ui/List';
import {Card, CardTitle, CardText, CardActions} from 'material-ui/Card';
import Container from 'muicss/lib/react/container';
import { hashHistory } from 'react-router';
import Notifier from '../notify/Notifier';
import './InventoryListing.css';


class InventoryListing extends Component {


  constructor(props) {
    super(props);

    this.state = {
      open: false,
      refreshing: false,
      itemsResult: {
          took: 0,
          hits: 0,
          list: []
      },
      viewItemDialogActions: [
        <RaisedButton
            label="Close"
            primary={true}
            onTouchTap={this.handleClose}
        />
      ],
      currentItemDetails: {}
    };
  }


componentWillMount() {
    this.refreshItems();
}

buyItem = (itemLinks, itemRefId) =>
{
    var authToken = sessionStorage.getItem("authToken");

    if (!authToken){
        Notifier.info("You must be logged in to buy an item.");
        hashHistory.push('/login');
        return;
    }
    var itemPurchaseUrl = itemLinks["related x-buy x-auth-required"].href;
    console.log("Attempting to buy item: " + itemRefId + ", purchaseUrl: " + itemPurchaseUrl);

    var requestBody = {};

    fetch(itemPurchaseUrl, {
        method: 'POST',  
        headers: {
        "Accept" : "application/json",
        "Content-Type" : "application/json",
        "X-Forwarded-Host" : window.location.hostname + ":" + window.location.port,
        "X-AUTH-TOKEN" : authToken
      },
      body: JSON.stringify(requestBody)
    })
    .then(  
        response => {
            if (response.status !== 200) {
                var errMsg = 'There was a problem buying this item. Status Code: ' +  response.status;
                console.log(errMsg);
                Notifier.error(errMsg);
                return;  
            }


            // Examine the text in the response  
            response.json().then(
                data => {  
                    console.log(data);
                    var msg = "Item '" + data.name + "'"
                        + " and refId '" + data.refId + "'"
                        + " successfully purchased for $" + data.purchasePrice;
                    console.log(msg);
                    Notifier.info(msg);
                    this.refreshItems();
        });
    })
    .catch(function(err) {
        console.log('Fetch Error :-S', err);
        Notifier.error("Unable to buy inventory item, reason: " + err);
    });

}


  handleOpen = (itemDetails) => {
    this.setState(
        {
            open: true,
            currentItemDetails: itemDetails
        });
  };

  handleClose = () => {
    this.setState({open: false, currentItemDetails: {}});
  };

viewItemDetails = (itemLinks, itemRefId) =>
{
    var itemDetailsUrl = itemLinks["self"].href;
    console.log("Viewing item details: " + itemRefId + ", itemDetailsUrl: " + itemDetailsUrl);

    fetch(itemDetailsUrl, {
        method: 'GET',  
        headers: {
        "Accept" : "application/json",
        "X-Forwarded-Host" : window.location.hostname + ":" + window.location.port
      }
    })
    .then(  
        response => {
            if (response.status !== 200) {
                var errMsg = 'There was a problem viewing this item. Status Code: ' +  response.status;
                console.log(errMsg);
                Notifier.error(errMsg);
                return;  
            }


            // Examine the text in the response  
            response.json().then(
                data => {  
                    console.log(data);
                    
                    this.handleOpen(data);
                    
                    //Refresh items because the price may have changed
                    this.refreshItems();
        });
    })
    .catch(function(err) {
        console.log('Fetch Error :-S', err);
        Notifier.error("Unable to buy inventory item, reason: " + err);
    });

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
                quantityInStock: item.quantityInStock,
                links: item._links,
                refId: item.externalReferenceId
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
        "Accept" : "application/json",
        "X-Forwarded-Host" : window.location.hostname + ":" + window.location.port
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
                Notifier.info("Retrieved " + this.state.itemsResult.count + " items");
        });
    })
    .catch(function(err) {
        console.log('Fetch Error :-S', err);
        Notifier.error("Unable to retrieve inventory items, reason: " + err);
    });
  }
  


  render() {
    return (
        <Container fluid={true}>
            <h1>Inventory Listing</h1>

            <div className="horiz">
                <div className="horiz">
                    <IconButton tooltip="Refresh items" onTouchTap={this.refreshItems}>
                        <ActionRefresh />
                    </IconButton>
                </div>
                <h3 className="horiz" >Items found: {this.state.itemsResult.count}</h3>

            </div>

            <Dialog ref="itemDetailDialog"
                    title="Inventory Item Details"
                    modal={true}
                    open={this.state.open}
                    actions={this.state.viewItemDialogActions}
                    >
                <div>
                    <div className="horiz">
                        <label className="item-label">Name:</label>
                    </div>
                    <div className="horiz">
                        <span>{this.state.currentItemDetails.name}</span>
                    </div>
                </div>
                <div>
                    <div className="horiz">
                        <label className="item-label">Description:</label>
                    </div>
                    <div className="horiz">
                        <span>{this.state.currentItemDetails.description}</span>
                    </div>
                </div>
                <div>
                    <div className="horiz">
                        <label className="item-label">Price:</label>
                    </div>
                    <div className="horiz">
                        <span>${this.state.currentItemDetails.price}</span>
                    </div>
                </div>
                <div>
                    <div className="horiz">
                        <label className="item-label">Quanity In-stock:</label>
                    </div>
                    <div className="horiz">
                        <span>${this.state.currentItemDetails.quantityInStock}</span>
                    </div>
                </div>
                <div>
                    <div className="horiz">
                        <label className="item-label">RefId:</label>
                    </div>
                    <div className="horiz">
                        <span>${this.state.currentItemDetails.externalReferenceId}</span>
                    </div>
                </div>
            </Dialog>
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
                            <FlatButton label="View Details" onTouchTap={() => this.viewItemDetails(row.links, row.refId)}/>
                            <RaisedButton label="Buy this item" onTouchTap={() => this.buyItem(row.links, row.refId)}/>
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