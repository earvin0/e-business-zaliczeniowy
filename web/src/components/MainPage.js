import React,{Category} from 'react';
import CategorySideBar from './CategorySideBar'
import './MainPage.css'
import ProductsList from "./ProductsList";
import {} from 'mdbreact'

class MainPage extends React.Component{

    render () {
        return (
            <div className={"container-fluid"}>
                <div className={"row"}>
                    <div className={"col-sm-2"}>
                        <CategorySideBar/>
                    </div>

                    <div className={"col-sm-8"}>
                        <ProductsList/>
                    </div>
                </div>
            </div>
        );
    }
}

export default MainPage