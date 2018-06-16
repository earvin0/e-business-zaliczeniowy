import React, { Component } from 'react';
import './App.css';
import MainPage from './components/MainPage'
import SearchBar from './components/SearchBar'

class App extends Component {
  render() {
    return (
      <div className="App">
          <div id={"topbar"}>
              <img id="logo" src="logo.png" alt={"Logo"}/>
              <div id={"login_div"}>Zalogowano jako OskarJ</div>
          </div>
          <div>
            <SearchBar/>
          </div>
          <MainPage/>
      </div>


    );
  }
}

export default App;
