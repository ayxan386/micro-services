import React, { Component } from "react";

export class Commenter extends Component {
  constructor(props) {
    super(props);
    this.state = {
      body: "",
    };
  }

  handleSubmit = (e) => {
    e.preventDefault();
    this.props.makeComment(this.state.body);
    this.setState({
      body: "",
    });
  };

  handleChange = (e) => {
    const temp = {};
    temp[e.target.name] = e.target.value;
    this.setState(temp);
  };

  render() {
    return (
      <div className='commenter'>
        <form onSubmit={this.handleSubmit} id='commenter'>
          <h4>Add your comment</h4>
          <textarea
            type='text'
            name='body'
            className='form-control'
            onChange={this.handleChange}
            value={this.state.body}
          />
          <button type='submit' className='btn btn-success'>
            POST
          </button>
        </form>
      </div>
    );
  }
}

export default Commenter;
