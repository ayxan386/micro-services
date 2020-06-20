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
