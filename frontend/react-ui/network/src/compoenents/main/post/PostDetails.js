import React, { Component } from "react";
import Comments from "../comment/Comments";

export class PostDetails extends Component {
  render() {
    if (!this.props.post) {
      return null;
    }
    const { title, body, nickname } = this.props.post;
    return (
      <div className='post-details'>
        <div className='post'>
          <div className='title'>
            <b>{title}</b>
          </div>
          <div className='body'>{body}</div>
          <div className='author'>by {nickname}</div>
        </div>
        <Comments comments={this.props.comments || []} />
      </div>
    );
  }
}

export default PostDetails;
