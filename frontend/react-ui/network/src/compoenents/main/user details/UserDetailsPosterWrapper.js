import React, { Component } from "react";
import UserDetails from "./UserDetails";
import Poster from "../post/Poster";
import { getMyDetails, updatePhoto } from "../../../actions/UserActions";
import { connect } from "react-redux";
import { createPost } from "../../../actions/PostActions";

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

  render() {
    return (
      <div className='right-wrapper'>
        <UserDetails
          className='user-details'
          handleFileSending={this.handleFileSending}
          userDetails={this.props.user}></UserDetails>
        <Poster post={this.createPost}></Poster>
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
    createPost: (title, body) => dispatch(createPost(title, body)),
  };
};
export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UserDetailsPosterWrapper);
