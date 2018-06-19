import React from 'react';
import { Button, Card, CardBody, CardTitle, CardText } from 'mdbreact';
import ReactModal from 'react-modal';


class ProductsList extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            products: [],
            query: "" ,
            category: "",
            showAddReviewModal: false,
            showRevModal: false,
            reviews: []
        };
        this.getProducts = this.getProducts.bind(this);
        this.getReviews = this.getReviews.bind(this);
        this.handleOpenAddRevModal = this.handleOpenAddRevModal.bind(this);
        this.handleCloseAddRevModal = this.handleCloseAddRevModal.bind(this);
        this.handleOpenRevModal = this.handleOpenRevModal.bind(this);
        this.handleCloseRevModal = this.handleCloseRevModal.bind(this);
    }

    handleOpenAddRevModal () {
        this.setState({ showAddReviewModal: true });
    }

    handleCloseAddRevModal () {
        console.log("Review: " + this.review.value);
        console.log("id: "+this.name)
        let data = {
            method: 'POST',
            body: JSON.stringify({
                review: this.review.value,
                grade: 1,
                userID: 1,
                productID: 1
            }),
            headers: {
                'Accept':       'application/json',
                'Content-Type': 'application/json'
            }
        };
        fetch("/api/addReview", data).then( form => console.log("Form sent: " + form));
        this.setState({ showAddReviewModal: false });
    }

    handleOpenRevModal () {
        this.setState({ showRevModal: true });
    }

    handleCloseRevModal () {
        this.setState({ showRevModal: false });
    }



    getAllProducts() {
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

    getReviews() {
        fetch('/api/getReviews',).then( (response) =>  {return response.json()} ).then(
            json => {
                this.setState({reviews: json})
            }

        );
    }


    componentDidMount() {
        this.getAllProducts();
        this.getReviews();
    }

    updateList(cat){
        this.setState({category: cat}, () =>  this.render());
    }

    addToCart(id){

        var item = this.state.products.find(item => item.id === 1);
        this.props.handleAddingToCart(item);
    }

    render () {

        //console.log(this.state.products);

        var cards = this.state.products.map(product => {
            if (product.category === this.state.category || this.state.category === ""){

                var reviews = this.state.reviews.map(review => {
                    if (review.productID === product.id ) {
                        return (<p className="h4 text-center mb-4" key={review.review}>{review.review}<br/></p>)
                    } else {
                        return null
                    }
                });

                return (

                    <Card key={product.name}>
                        <CardBody>
                            <CardTitle>{product.name}</CardTitle>
                            <CardText>{product.description}</CardText>
                            <Button data={product.id} onClick={(data) => this.addToCart(data)}>Add to cart</Button>


                            <Button onClick={this.handleOpenRevModal}>Show reviews</Button>

                            <ReactModal
                                isOpen={this.state.showRevModal}
                                contentLabel="Minimal Modal Example"
                                id={product.id}
                                ariaHideApp={false}>

                                <div className="modal-content" >
                                    <div className="modal-header">
                                        {reviews}
                                    </div>

                                        <div className="text-center mt-4">
                                            <button className="btn btn-default"onClick={this.handleCloseRevModal}>Close</button>
                                        </div>
                                </div>
                            </ReactModal>

                            <Button onClick={this.handleOpenAddRevModal} >Add review</Button>

                            <ReactModal
                                isOpen={this.state.showAddReviewModal}
                                contentLabel="Minimal Modal Example"
                                id={"addReviewModal"}
                                ariaHideApp={false}>

                                <div className="modal-content" >
                                    <div className="modal-header">
                                        <p className="h4 text-center mb-4">Add review for:<br/></p>
                                        <p className="h6 text-center mb-4">{product.name}</p>
                                    </div>
                                    <form method={"POST"} action={"/api/addReview"}>
                                        <div className="md-form">
                                            <label htmlFor="review">Add review:</label>
                                            <textarea className="form-control" rows="5" id="review" name={"review"} ref={(review) => this.review = review}/>
                                        </div>
                                        <div className="text-center mt-4">
                                            <button className="btn btn-default" type="submit"onClick={this.handleCloseAddRevModal}>Send and close</button>
                                        </div>
                                    </form>
                                </div>
                            </ReactModal>
                        </CardBody>
                    </Card>
                )
            } else {
                return null;
            }

        });
        return (
            <div>
                <div className={"input-group md-form form-sm form-1 pl-0"}>
                    <div className="form-group">
                        <input type={"text"} defaultValue={""} className={"form-control my-0 py-1"} placeholder={"search"} name={"searchQuery"} ref={query => this.query = query}/>

                    </div>
                    <div className="form-group">
                        <Button className={"btn btn-default btn-sm"} onClick={this.getProducts}>Search</Button>
                    </div>
                </div>

                {cards}

            </div>
        );
    }
}

export default ProductsList