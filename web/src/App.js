import React, { Component } from 'react';
import './App.css';
import MainPage from './components/MainPage';
import axios from 'axios';

class App extends Component {

  constructor(props) {
      super(props);
      this.state = {isLoggedIn: false};
  }

  componentDidMount() {
      axios.get('/api/getCurrentUser').then(data => {
              localStorage.setItem("USER_ID",data.data.name);
              localStorage.setItem("userToken",data.data.token)
      });
  }


  render() {
    let login;
    var userID = localStorage.getItem("USER_ID");
    if ( userID && userID !== "undefined"){
        login = <div id={"login_div"}>Zalogowano jako {userID} | <a href='http://localhost:9000/signOut'>Wyloguj</a></div>
    } else {
        login = <a href='http://localhost:9000/signIn'><div id={"login_div"}>Zaloguj</div></a>
    }
    return (
      <div className="App">
          <div id={"topbar"}>
              <img id="logo" src="logo.png" alt={"Logo"}/>
              {login}

          </div>
          <MainPage/>
      </div>


    );
  }
}

export default App;
