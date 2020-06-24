import React, { Component } from "react";
import InformationField from "./InformationField";
import "../../../styles/main.css";

export class UserDetails extends Component {
  constructor(props) {
    super(props);
    this.state = {
      file: null,
    };
  }

  sendFile = (e) => {
    e.preventDefault();
    this.props.handleFileSending(this.state.file);
  };

  handleChange = (e) => {
    this.setState({
      file: e.target.files[0],
    });
  };

  updateUser = (fieldName, fieldValue) => {
    const temp = {};
    temp[fieldName.toLowerCase()] = fieldValue;
    const body = {
      ...this.props.userDetails,
      ...temp,
    };
    this.props.updateUserDetails(body);
  };

  render() {
    if (this.props.userDetails) {
      const {
        name,
        surname,
        nickname,
        profilePicture,
        email,
      } = this.props.userDetails;
      return (
        <div className='user-details'>
          <div id='general-information'>
            <InformationField
              name='Name'
              value={name}
              updateUser={this.updateUser}
            />
            <InformationField
              name='Surname'
              value={surname}
              updateUser={this.updateUser}
            />
            <InformationField
              name='Email'
              value={email}
              updateUser={this.updateUser}
            />
            <InformationField
              name='Nickname'
              value={nickname}
              updateUser={this.updateUser}
            />
          </div>
          <div id='profile-picture'>
            <img src={profilePicture} alt='profile'></img>
            <form id='profile-form' onSubmit={this.sendFile}>
              <input
                type='file'
                id='file'
                name='file'
                onChange={this.handleChange}
              />
              <button type='submit' className='btn btn-dark'>
                Change Photo
              </button>
            </form>
          </div>
        </div>
      );
    } else {
      return <div className='user-details'></div>;
    }
  }
}

export default UserDetails;
