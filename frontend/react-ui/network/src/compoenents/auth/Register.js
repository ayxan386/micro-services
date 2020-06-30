import React, { Component } from "react";
import { register } from "../../actions/AuthActions";
import { connect } from "react-redux";
import { Helmet } from "react-helmet";
import { Redirect, Link } from "react-router-dom";
import "../../styles/bootstrap.min.css";

class Register extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      password: "",
      cpassword: "",
      isActive: false,
      isMatching: false,
    };
  }

  register = (e) => {
    e.preventDefault();
    this.props.register(this.state.username, this.state.password, "USER");
  };

  checkMatching = (cpassword) => {
    this.setState({
      isMatching: this.state.password === cpassword,
      isActive: true,
    });
  };

  handleChange = (e) => {
    let temp = {};
    temp[e.target.name] = e.target.value;
    this.setState(temp);
    if (e.target.name === "cpassword") {
      this.checkMatching(e.target.value);
    } else {
      this.setState({
        isActive: false,
      });
    }
  };

  render() {
    const { isRegistered } = this.props;
    const inputClassName = this.state.isActive
      ? this.state.isMatching
        ? "is-valid"
        : "is-invalid"
      : "dummy";
    return (
      <div className='main-div'>
        <Helmet>
          <title>Register Page</title>
        </Helmet>
        {isRegistered === true ? (
          <Redirect to='/mainPage'></Redirect>
        ) : (
          <form className='main-form' onSubmit={this.register}>
            <div className='form-group'>
              <label htmlFor='username'>Username:</label>
              <input
                name='username'
                id='username'
                placeholder='Choose Username:'
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
                placeholder='Enter Strong Password:'
                className='form-control'
                onChange={this.handleChange}
              />
            </div>
            <div className='form-group'>
              <label htmlFor='password'>Repeat password:</label>
              <input
                name='cpassword'
                type='password'
                id='cpassword'
                value={this.state.cpassword}
                placeholder='Repeat Password:'
                className={`form-control ${inputClassName}`}
                onChange={this.handleChange}
              />
            </div>

            <div className='button-holder'>
              <button className='btn btn-dark' type='submit'>
                Sign Up
              </button>
            </div>
            <div className='link'>
              <Link to='/login'>Already have account?</Link>
            </div>
          </form>
        )}
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  const isRegistered = state.auth.isRegistered;
  return {
    isRegistered: isRegistered,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    register: (username, password, roles) =>
      dispatch(register(username, password, roles)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(Register);
