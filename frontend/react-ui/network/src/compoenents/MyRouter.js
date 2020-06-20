import React, { Component } from "react";
import { BrowserRouter, Route } from "react-router-dom";
import Login from "./auth/Login";
import MainPage from "./main/MainPage";

export class MyRouter extends Component {
  render() {
    return (
      <BrowserRouter>
        <Route path='/login' component={Login}></Route>
        <Route path='/mainPage' component={MainPage}></Route>
      </BrowserRouter>
    );
  }
}

export default MyRouter;
