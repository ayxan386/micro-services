import React, { Component } from "react";

export class Poster extends Component {
  constructor(props) {
    super(props);
    this.state = {
      title: "",
      body: "",
    };
  }

  handleSubmit = (e) => {
    e.preventDefault();
    this.props.post(this.state.title, this.state.body);
  };

  handleChange = (e) => {
    const temp = {};
    temp[e.target.name] = e.target.value;
    this.setState(temp);
  };

  render() {
    return (
      <div className='poster'>
        <form onSubmit={this.handleSubmit} id='poster'>
          <h3>Post something</h3>
          <input
            type='text'
            name='title'
            className='form-control'
            onChange={this.handleChange}
            value={this.state.title}
            placeholder='Title'
          />
          <textarea
            type='text'
            name='body'
            className='form-control textarea'
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

export default Poster;
