import React, { Component } from "react";
import PostDetails from "./PostDetails";
import "../../../styles/postdetails.css";
import Commenter from "../comment/Commenter";

export class PostDetailsCommenterWrapper extends Component {
  getPost = () => {
    console.log(this.props.posts);
    console.log(this.props.postId);
    console.log(
      this.props.posts.filter((post) => post.id === this.props.postId)
    );
    return this.props.posts.filter((post) => post.id === this.props.postId)[0];
  };
  render() {
    return (
      <div className='right-wrapper-post'>
        <PostDetails post={this.getPost()} />
        <Commenter />
      </div>
    );
  }
}

export default PostDetailsCommenterWrapper;
