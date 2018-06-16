import React from 'react';

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
            <SideNav>
                {this.state.categories.map( (category) => {
                        return (
                            <Nav><NavText>{category.name}</NavText></Nav>
                        )
                    }

                )}
            </SideNav>
        );
    }
}

export default CartSideBar