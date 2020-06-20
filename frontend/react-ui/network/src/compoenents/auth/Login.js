import React, { Component } from "react";
import { login } from "../../actions/AuthActions";
import { connect } from "react-redux";
import { Helmet } from "react-helmet";
import { Redirect } from "react-router-dom";

export class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      password: "",
    };
  }
  login = (e) => {
    e.preventDefault();
    this.props.login(this.state.username, this.state.password);
  };
  handleChange = (e) => {
    let temp = {};
    temp[e.target.name] = e.target.value;
    this.setState(temp);
  };
  render() {
    const { isLogged } = this.props;
    return (
      <div className='main-div'>
        <Helmet>
          <title>Login Page</title>
        </Helmet>
        {isLogged === true ? (
          <Redirect to='/mainPage'></Redirect>
        ) : (
          <form className='main-form' onSubmit={this.login}>
            <div className='form-group'>
              <label htmlFor='username'>Username:</label>
              <input
                name='username'
                id='username'
                placeholder='Enter Username:'
                type='text'
                className='form-control'
                value={this.state.username}
                onChange={this.handleChange}
              />
            </div>
            <div className='form-group'>
              <label htmlFor='password'>Password:</label>
              <input
                name='password'
                type='password'
                id='password'
                value={this.state.password}
                placeholder='Enter Password:'
                className='form-control'
                onChange={this.handleChange}
              />
            </div>
            <div className='button-holder'>
              <button className='btn btn-dark' type='submit'>
                Login
              </button>
            </div>
          </form>
        )}
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  const isLogged = state.auth.isLogged;
  return {
    foo: "hello",
    isLogged: isLogged,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    login: (username, password) => dispatch(login(username, password)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(Login);
