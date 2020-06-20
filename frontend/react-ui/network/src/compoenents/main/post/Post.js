import React, { Component } from "react";

export class Post extends Component {
  render() {
    return (
      <div
        className='post'
        onClick={() => this.props.onClickCallBack(this.props.id)}>
        <div className='title'>
          <b>{this.props.title}</b>
        </div>
        <div className='body'>{this.props.body}</div>
        <div className='author'>by {this.props.author.nickname}</div>
      </div>
    );
  }
}

export default Post;
