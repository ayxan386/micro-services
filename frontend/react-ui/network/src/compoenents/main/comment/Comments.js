import React, { Component } from "react";
import Comment from "./Comment";

export class Comments extends Component {
  render() {
    return (
      <div className='comments'>
        {this.props.comments.map((comment) => {
          return (
            <Comment
              key={comment.commentId || Math.floor(Math.random() * 1000)}
              id={comment.commentId}
              body={comment.body}
              author={comment.author}
            />
          );
        })}
      </div>
    );
  }
}

export default Comments;
