import React from 'react';
import { Button, Card, CardBody, CardTitle, CardText } from 'mdbreact';


class ProductsList extends React.Component{
    constructor(props){
        super(props);
        this.state = { products: [], query: "" }
        this.getProducts = this.getProducts.bind(this);

    }

    getAllProducts() {
        console.log(this.state.products);
        fetch('/api/getProducts').then( (response) =>  {return response.json()} ).then(
            json =>
                this.setState({products: json}, () => this.render())
        );

    }

    getProducts(){
        fetch('/api/getByKeyword', {
                method: 'post',
                headers: {
                    'Accept':       'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ 'keyword': this.query.value})
            }).then( (response) =>  {return response.json()} ).then(
                json =>
                    this.setState({products: json}, () => this.render())
        );

    }


    componentDidMount() {
        this.getAllProducts();
    }

    render () {

        console.log(this.state.products);

        var cards = this.state.products.map(product => {
            return (
                <Card key={product.name}>
                    <CardBody>
                        <CardTitle>{product.name}</CardTitle>
                        <CardText>{product.category}</CardText>
                        <Button href="#">Button</Button>
                    </CardBody>
                </Card>
            );
        });
        return (
            <div>
                <div className={"input-group md-form form-sm form-1 pl-0"}>
                    <div className="form-group">
                        <input type={"text"} defaultValue={""} className={"form-control my-0 py-1"} placeholder={"search"} name={"searchQuery"} ref={query => this.query = query}/>

                    </div>
                    <div className="form-group">
                        <Button className={"btn btn-default"} onClick={this.getProducts}>Search</Button>
                    </div>
                </div>

                {cards}

            </div>
        );
    }
}

export default ProductsList