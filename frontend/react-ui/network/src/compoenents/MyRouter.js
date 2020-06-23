import React, { Component } from "react";
import { BrowserRouter, Route } from "react-router-dom";
import Login from "./auth/Login";
import MainPage from "./main/MainPage";
import Logout from "./auth/Logout";
import Register from "./auth/Register";

export class MyRouter extends Component {
  render() {
    return (
      <BrowserRouter>
        <Route path='/login' component={Login}></Route>
        <Route path='/logout' component={Logout}></Route>
        <Route path='/register' component={Register}></Route>
        <Route path='/mainPage' component={MainPage}></Route>
        <Route path='/' component={MainPage}></Route>
      </BrowserRouter>
    );
  }
}

export default MyRouter;
