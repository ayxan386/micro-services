import React, { Component } from "react";
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
  render() {
    if (this.props.userDetails) {
      const {
        name,
        surname,
        nickname,
        profilePicture,
      } = this.props.userDetails;
      return (
        <div className='user-details'>
          <div id='general-information'>
            <div>
              <div className='info'>Name:</div>
              <div className='info'>Surname:</div>
              <div className='info'>Nickname:</div>
            </div>
            <div>
              <div>{name}</div>
              <div>{surname}</div>
              <div>{nickname}</div>
            </div>
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
