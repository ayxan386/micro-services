import React, { Component } from "react";
import Comments from "../comment/Comments";

export class PostDetails extends Component {
  render() {
    if (!this.props.post) {
      return null;
    }
    const { title, body, comments } = this.props.post;
    const { nickname } = this.props.post.author;
    return (
      <div className='post-details'>
        <div className='post'>
          <div className='title'>
            <b>{title}</b>
          </div>
          <div className='body'>{body}</div>
          <div className='author'>by {nickname}</div>
        </div>
        <Comments
          comments={
            comments.length > 0
              ? comments || [
                  {
                    body: "Be first to comment on this post",
                  },
                ]
              : [
                  {
                    body: "Be first to comment on this post",
                  },
                ]
          }
        />
      </div>
    );
  }
}

export default PostDetails;
