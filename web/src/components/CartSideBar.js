import React from 'react';
import { ListGroup, ListGroupItem, Badge } from 'mdbreact'
import ReactModal from 'react-modal';

class CartSideBar extends React.Component{
    constructor(props){
        super(props);
        this.state = { showCheckoutModal: false, items: [ ]}
    }

    addToCart(it) {
        console.log("items");
        console.log(this.state.items);
        this.setState(prevState => ({
            items: [...prevState.items,it]
        }));
        console.log(this.state.items);

    }

    removeFromCart = (id) => {
        var idToDelete = id;
        var newItems = this.state.items.filter(item=>item.id !==idToDelete );
        this.setState({ items: newItems });
    }



    handleOpenCheckOutModal  = () => {
        this.setState({ showCheckoutModal: true });
    }

    handleCloseRevModal = () => {
        this.setState({ showCheckoutModal: false });
    }


    render () {

        var items = this.state.items.map(item => {
            return (<ListGroupItem key={item.name}>{item.name}<Badge onClick={() => this.removeFromCart(item.id)} color="primary" pill>X</Badge></ListGroupItem>)
        });

        if ( !this.state.items.length ) {
            items = (<ListGroupItem key={"zero"}>dodaj coś do koszyka</ListGroupItem>)
        }
        return (
            <div>
                <h3>Koszyk</h3>
                <ListGroup>
                    {items}
                    <ListGroupItem key={"button"}><button className="btn btn-default"onClick={this.handleOpenCheckOutModal}>Check out</button></ListGroupItem>
                    <ReactModal
                        isOpen={this.state.showCheckoutModal}
                        contentLabel="Minimal Modal Example"
                        id={"CheckoutModal"}
                        ariaHideApp={false}>

                        <div className="modal-content" >
                            <div className="modal-header">
                                <p className="h4 text-center mb-4">To be continued...<br/></p>
                            </div>
                            <button className="btn btn-default" onClick={this.handleCloseRevModal}>Close</button>

                        </div>
                    </ReactModal>
                </ListGroup>
            </div>
        );
    }
}

export default CartSideBar