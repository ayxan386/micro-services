import React, { Component } from "react";
import PostDetails from "./PostDetails";
import "../../../styles/postdetails.css";
import Commenter from "../comment/Commenter";
import { connect } from "react-redux";
import { makeComment } from "../../../actions/CommentActions";

export class PostDetailsCommenterWrapper extends Component {
  makeComment = (body) => {
    this.props.makeComment(body, this.getPost().id);
  };

  getPost = () => {
    return this.props.posts.filter((post) => post.id === this.props.postId)[0];
  };
  render() {
    return (
      <div className='right-wrapper-post'>
        <PostDetails post={this.getPost()} />
        <Commenter postId={this.getPost().id} makeComment={this.makeComment} />
        <div className='closing-button' onClick={this.props.closeDetails}>
          <b>X</b>
        </div>
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  return {};
};

const mapDispatchToProps = (dispatch) => {
  return {
    makeComment: (commentBody, postId) =>
      dispatch(makeComment(commentBody, postId)),
  };
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PostDetailsCommenterWrapper);
