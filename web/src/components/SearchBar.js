import React from 'react';

class SearchBar extends React.Component{


    render () {
        return (
            <form className={"form-inline float"} action={"/asdasd.txt"} method={"post"}>
                <div className="form-group">

                    <input type={"text"} className={"form-control"} placeholder={"search"} name={"searchQuery"}/>


                </div>
                <input type={"submit"} className={"btn btn-default"} value ="submit"/>
            </form>
        );
    }
}

export default SearchBar