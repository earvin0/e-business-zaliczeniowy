import React from 'react';
import CategorySideBar from './CategorySideBar'
import './MainPage.css'
import ProductsList from "./ProductsList";
import 'mdbreact'
import CartSideBar from './CartSideBar'

class MainPage extends React.Component{

    constructor(props){
        super(props);
        this.state = {products : [],searchQuery: "", category: ""}

    }

    fetchProducts = () => {
        fetch('http://localhost:9090/api/getByKeyword',{
            method: 'post',
            headers: {'Content-Type':'application/json'},
            body: {
                "keyword": this.state.searchQuery
            }
        }
        ).then( json => json.json()).then(jsons => this.setState({products: jsons }))
    }

    getSearchQuery = (query) => {
        this.setState({searchQuery : query});
    }

    chooseCategory(){

    }

    render () {
        return (
            <div className={"container-fluid"}>
                <div className={"row"}>
                    <div className={"col-sm-2"}>
                        <p>{this.state.searchQuery}</p>
                        <CategorySideBar/>
                    </div>

                    <div className={"col-sm-7"}>
                        <ProductsList productsData={this.state.products}/>
                    </div>
                    <div className={"col-sm-3"}>
                        <CartSideBar/>
                    </div>
                </div>
            </div>
        );
    }
}

export default MainPage