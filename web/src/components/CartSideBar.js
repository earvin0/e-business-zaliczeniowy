import React from 'react';
import { ListGroup, ListGroupItem, Badge } from 'mdbreact'

class CartSideBar extends React.Component{
    constructor(props){
        super(props);
        this.state = { items: [
                {
                    "id": 1,
                    "name": "sample"
                },
                {
                    "id": 2,
                    "name": "asd"
                },
                {
                    "id": 3,
                    "name": "dsa"
                }
            ]}
    }


    render () {
        return (
            <div>
                <h3>Koszyk</h3>
                <ListGroup>
                    <ListGroupItem>Cras justo odio<Badge color="primary" pill>14</Badge></ListGroupItem>
                    <ListGroupItem>Dapibus ac facilisis in<Badge color="primary" pill>2</Badge></ListGroupItem>
                    <ListGroupItem>Morbi leo risus<Badge color="primary" pill>1</Badge></ListGroupItem>
                </ListGroup>
            </div>
        );
    }
}

export default CartSideBar