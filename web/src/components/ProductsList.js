import React from 'react';
import { Button, Card, CardBody, CardImage, CardTitle, CardText } from 'mdbreact';


class ProductsList extends React.Component{
    constructor(props){
        super(props);
        this.state = { products: [
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
                <Card>
                    <CardBody>
                        <CardTitle>Card title</CardTitle>
                        <CardText>Some quick example text to build on the card title and make up the bulk of the card's content.</CardText>
                        <Button href="#">Button</Button>
                    </CardBody>
                </Card>
                <Card>
                    <CardBody>
                        <CardTitle>Card title</CardTitle>
                        <CardText>Some quick example text to build on the card title and make up the bulk of the card's content.</CardText>
                        <Button href="#">Button</Button>
                    </CardBody>
                </Card>
            </div>
        );
    }
}

export default ProductsList