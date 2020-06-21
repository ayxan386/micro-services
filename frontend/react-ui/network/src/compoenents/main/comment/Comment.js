import React, { Component } from "react";

export class Comment extends Component {
  render() {
    return (
      <div className='comment'>
        {this.props.author ? (
          <div className='author'>
            <img
              className='img'
              src={this.props.author.profilePicture}
              alt='commenter'
            />
            {this.props.author.nickname}
          </div>
        ) : null}
        <div className='body'>{this.props.body}</div>
      </div>
    );
  }
}

export default Comment;
