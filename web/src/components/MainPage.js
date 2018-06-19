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


    handleCategorySelection(selection){
        this.setState({category: selection});
        this.foo.updateList(selection);
    }

    handleAddingToCart(item){
        this.foo2.addToCart(item);
    }

    render () {
        return (
            <div className={"container-fluid"}>
                <div className={"row"}>
                    <div className={"col-sm-2"}>
                        <p>{this.state.searchQuery}</p>
                        <CategorySideBar handleCategorySelection={this.handleCategorySelection.bind(this)}/>
                    </div>

                    <div className={"col-sm-7"}>
                        <ProductsList ref={foo => {this.foo = foo}} handleAddingToCart={this.handleAddingToCart.bind(this)} productsData={this.state.products}/>
                    </div>
                    <div className={"col-sm-3"}>
                        <CartSideBar ref={foo2 => {this.foo2 = foo2}} />
                    </div>
                </div>
            </div>
        );
    }
}

export default MainPage