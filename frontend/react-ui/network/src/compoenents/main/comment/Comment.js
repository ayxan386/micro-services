import React, { Component } from "react";

export class Comment extends Component {
  render() {
    return (
      <div className='comment'>
        <div className='author'>
          <img className='img' src={this.props.author} alt='commenter' />
          {this.props.author}
        </div>
        <div className='body'>{this.props.body}</div>
      </div>
    );
  }
}

export default Comment;
