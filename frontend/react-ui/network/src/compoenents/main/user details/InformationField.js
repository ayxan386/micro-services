import React, { Component } from "react";
import { FiEdit } from "react-icons/fi";
import { FaSave } from "react-icons/fa";

export class InformationField extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isEditing: false,
      value: "",
    };
  }

  componentDidMount() {
    this.setState({
      value: this.props.value,
    });
  }

  toggleField = (name) => {
    if (name === "save") {
      this.props.updateUser(this.props.name, this.state.value);
    }
    this.setState({
      isEditing: !this.state.isEditing,
    });
  };

  handleChange = (e) => {
    this.setState({
      value: e.target.value,
      startedEditing: true,
    });
  };

  render() {
    return (
      <div className='information-field'>
        <div className='label'>{this.props.name}: </div>
        <input
          disabled={!this.state.isEditing}
          name={this.props.name}
          value={
            this.state.value !== undefined ? this.state.value : this.props.value
          }
          onChange={this.handleChange}
          className={`input ${
            this.state.isEditing ? "input-enabled" : "dummy"
          } ${this.props.className || "dummy2"}`}
        />
        {this.state.isEditing ? (
          <button
            type='button'
            className='edit-button'
            onClick={() => this.toggleField("save")}
            name='save'>
            <FaSave />
          </button>
        ) : (
          <button
            type='button'
            className='edit-button'
            onClick={() => this.toggleField("edit")}
            name='edit'>
            <FiEdit />
          </button>
        )}
      </div>
    );
  }
}

export default InformationField;
