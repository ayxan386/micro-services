import React, { Component } from "react";
import { Redirect } from "react-router-dom";
import { connect } from "react-redux";
import { logout } from "../../actions/AuthActions";

export class Logout extends Component {
  componentDidMount() {
    this.props.logout();
  }

  render() {
    return (
      <div>
        <Redirect to='/login' />
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  return {};
};

const mapDispatchToProps = (dispatch) => {
  return {
    logout: () => dispatch(logout()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(Logout);
