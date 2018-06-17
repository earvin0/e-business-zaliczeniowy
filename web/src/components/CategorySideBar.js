import React from 'react';
import SideNav, { Nav, NavText } from 'react-sidenav';

class CategorySideBar extends React.Component{
    constructor(props){
        super(props);
        this.state = { categories: [
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

    componentDidMount() {
        fetch('http://localhost:9090/api/getCategories').then(function(response){
            if (response.status >= 400) {
                throw new Error("Bad response from server");
            }
            var data = response.json();
            this.setState({categories: data});
        }).catch(err => {
            throw new Error(err)
        });
    }


    render () {
        return (
            <SideNav highlightBgcolor={"black"}>
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

export default CategorySideBar