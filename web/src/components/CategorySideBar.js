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
        var that = this;
        fetch('/api/getCategories').then(function(response){
            if (response.status >= 400) {
                throw new Error("Bad response from server");
            }
            return response.json();
        }).then(function (data) {
            that.setState({categories: data});
        }).catch(err => {
            throw new Error(err)
        });
    }


    render () {
        return (
            <div>
                <h3>Kategorie</h3>
                <SideNav highlightBgcolor={"black"}>
                    {this.state.categories.map( (category) => {
                        return (
                            <Nav key={category.name}><NavText>{category.name}</NavText></Nav>
                        )
                        }

                    )}
                </SideNav>
            </div>
        );
    }
}

export default CategorySideBar