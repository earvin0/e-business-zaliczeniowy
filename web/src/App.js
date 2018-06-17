import React, { Component } from 'react';
import './App.css';
import MainPage from './components/MainPage'

class App extends Component {
  render() {
    return (
      <div className="App">
          <div id={"topbar"}>
              <img id="logo" src="logo.png" alt={"Logo"}/>
              <div id={"login_div"}>Zalogowano jako OskarJ</div>
          </div>
          <MainPage/>
      </div>


    );
  }
}

export default App;
