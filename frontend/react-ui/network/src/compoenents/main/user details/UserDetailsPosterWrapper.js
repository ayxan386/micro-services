import React, { Component } from "react";
import UserDetails from "./UserDetails";
import Poster from "../post/Poster";
import {
  getMyDetails,
  updatePhoto,
  updateUserDetails,
} from "../../../actions/UserActions";
import { connect } from "react-redux";
import { createPost } from "../../../actions/PostActions";
import { logout } from "../../../actions/AuthActions";

export class UserDetailsPosterWrapper extends Component {
  componentDidMount() {
    this.props.getUserDetails();
  }
  handleFileSending = (file) => {
    this.props.updatePhoto(file);
  };

  createPost = (title, body) => {
    this.props.createPost(title, body);
  };

  logout = () => {
    this.props.logout();
  };

  render() {
    return (
      <div className='right-wrapper'>
        <UserDetails
          className='user-details'
          handleFileSending={this.handleFileSending}
          userDetails={this.props.user}
          updateUserDetails={this.props.updateUserDetails}
        />
        <Poster post={this.createPost}></Poster>
        <div className='logout-button-holder'>
          <button className='logout-button' onClick={this.logout}>
            Logout
          </button>
        </div>
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    user: state.user.userDetails,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getUserDetails: () => dispatch(getMyDetails()),
    updatePhoto: (file) => dispatch(updatePhoto(file)),
    updateUserDetails: (body) => dispatch(updateUserDetails(body)),
    createPost: (title, body) => dispatch(createPost(title, body)),
    logout: () => dispatch(logout()),
  };
};
export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UserDetailsPosterWrapper);
