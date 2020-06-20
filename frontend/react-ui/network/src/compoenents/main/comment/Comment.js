import React from "react";

export default function Comment() {
  return (
    <div className='comment'>
      <div className='body'>{this.props.body}</div>
      <div className='author'>{this.props.author}</div>
    </div>
  );
}
